package com.exe201.opalwed.security;

import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.Account;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.model.Role;
import com.exe201.opalwed.repository.AccountRepository;
import com.exe201.opalwed.repository.InformationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final InformationRepository informationRepository;

    public Map<String, Object> createLoginInfo(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        Account account = accountRepository.findAccountByEmail(request.getEmail())
                .orElseThrow(() -> new OpalException("Email not exists"));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new OpalException("Invalid password");
        }

        String token = jwtProvider.createToken(account);

        account.setPassword("");
        result.put("userInfo", account);
        result.put("token", token);

        return result;
    }

    public Map<String, Object> register(RegisterRequest request) {
        if (accountRepository.existsAccountByEmail(request.getEmail())) {
            throw new OpalException("Email exists");
        }

        Account newAccount = Account.builder()
                .email(request.getEmail())
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

        newInformation = informationRepository.save(newInformation);
        String token = jwtProvider.createToken(newInformation.getAccount());

        Map<String, Object> result = new HashMap<>();
        newAccount.setPassword("");
        result.put("userInfo", newAccount);
        result.put("token", token);

        return result;
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
                if(userAccountCheck.isPresent()){
                    userAccount = userAccountCheck.get();
                }else{
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
                throw new OpalException("Invalid Google token");
            }
        } catch (Exception e) {
            throw new OpalException("Login google failed");
        }
    }

}
