package com.exe201.opalwed.security;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.*;
import com.exe201.opalwed.repository.AccountRepository;
import com.exe201.opalwed.repository.InformationRepository;

import com.exe201.opalwed.repository.OTPInformationRepository;
import com.exe201.opalwed.service.OpalMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final InformationRepository informationRepository;
    private final OTPInformationRepository otpRepo;
    private final OpalMailService mailService;

    public Map<String, Object> createLoginInfo(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        Account account = accountRepository.findAccountByEmail(request.getEmail())
                .orElseThrow(() -> new OpalException("Email không tồn tại!"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new OpalException("Sai mật khẩu!");
        }

        String token = jwtProvider.createToken(account);

        account.setPassword("");
        result.put("userInfo", account);
        result.put("token", token);

        return result;
    }

    public ResponseObject register(RegisterRequest request) {
        Optional<Account> existAccount = accountRepository.findAccountByEmail(request.getEmail());
        if (existAccount.isPresent()) {
            if (existAccount.get().getStatus().equals(AccountStatus.PENDING)) {
                return updateOTPThenSend(existAccount.get());
            }
            throw new OpalException("Email đã tồn tại!");
        }

        Account newAccount = Account.builder()
                .email(request.getEmail())
                .status(AccountStatus.PENDING)
                .password(passwordEncoder.encode(request.getPassword()))
                .accountRole(Role.CUSTOMER)
                .build();
        Information newInformation = Information.builder()
                .account(newAccount)
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();

        informationRepository.save(newInformation);
        return ResponseObject.builder().isSuccess(true).status(HttpStatus.OK).message("Tạo tài khoản thành công!").build();
    }

    private ResponseObject updateOTPThenSend(Account account) {
        if (account.getOtp() != null) {
            account.getOtp().resetOTP();
        } else {
            account.setOtp(new OTPInformation());
        }
        account = accountRepository.save(account);
        try {
            mailService.sendOTP(account);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new OpalException("Gửi OTP thất bại, vui lòng thử lại sau!");
        }
        return ResponseObject.builder()
                .status(HttpStatus.OK).message("Gửi OTP thành công").isSuccess(true).build();
    }

    public ResponseObject changePassword(Authentication authentication, ChangePasswordRequest request) {

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new OpalException("Mật khẩu mới không trùng với mật khẩu xác nhận lại!");
        }
        Account account = accountRepository.findAccountByEmail(authentication.getName())
                .orElseThrow(() -> new OpalException("Tài khoản không tồn tại!"));
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new OpalException("Mật khẩu cũ không đúng!");
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
        return ResponseObject.builder()
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Đổi mật khẩu thành công!")
                .build();
    }


    public Map<String, Object> loginWithGoogle(String requestToken) {

        try {
            URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + requestToken);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                JsonParser jsonParser = JsonParserFactory.getJsonParser();
                Map<String, Object> jsonData = jsonParser.parseMap(response.toString());
                String email = (String) jsonData.get("email");
                String givenName = (String) jsonData.get("given_name");
                String picture = (String) jsonData.get("picture");
                Optional<Account> userAccountCheck = accountRepository.findAccountByEmail(email);
                Account userAccount;
                Information newInformation = null;
                if (userAccountCheck.isPresent()) {
                    userAccount = userAccountCheck.get();
                } else {
                    userAccount = Account.builder()
                            .email(email)
                            .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                            .accountRole(Role.CUSTOMER)
                            .build();
                    accountRepository.save(userAccount);
                    newInformation = Information.builder()
                            .account(userAccount)
                            .fullName(givenName)
                            .imageUrl(picture)
                            .build();
                    informationRepository.save(newInformation);
                }
                String jwtToken = jwtProvider.createToken(newInformation.getAccount());

                Map<String, Object> result = new HashMap<>();
                userAccount.setPassword("");
                result.put("userInfo", userAccount);
                result.put("token", jwtToken);

                return result;
            } else {
                throw new OpalException("Đăng nhập Google thất bại!");
            }
        } catch (Exception e) {
            throw new OpalException("Đăng nhập Google thất bại!");
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseObject sendOtp(String email) {
        Account account = accountRepository.findAccountByEmail(email).orElseThrow(() -> new OpalException("Tài khoản không tồn tại"));

        if (account.getStatus() == AccountStatus.ACTIVE) {
            return ResponseObject.builder()
                    .message("Tài khoản đã xác thực")
                    .isSuccess(false)
                    .status(HttpStatus.OK)
                    .build();
        }

        return updateOTPThenSend(account);
    }

    public ResponseObject validateOTP(ValidateOTPRequest request) {
        Account account = accountRepository.findAccountByEmail(request.getEmail()).orElseThrow(() -> new OpalException("Tài khoản không tồn tại"));

        OTPInformation otp = account.getOtp();
        if (!otp.getOtpCode().equals(request.otp)) {
            return ResponseObject.builder()
                    .message("Mã OTP không chính xác")
                    .isSuccess(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        if (LocalDateTime.now().isAfter(otp.getExpire())) {
            return ResponseObject.builder()
                    .message("Mã OTP hết hạn!")
                    .isSuccess(false)
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
        account.setStatus(AccountStatus.ACTIVE);
        account.setOtp(null);
        accountRepository.saveAndFlush(account);
        otpRepo.delete(otp);

        return ResponseObject.builder().status(HttpStatus.OK).isSuccess(true).message("Xác thực thành công!").build();
    }
}
