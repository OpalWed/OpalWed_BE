package com.exe201.opalwed.security;

import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.Account;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.model.Role;
import com.exe201.opalwed.repository.AccountRepository;
import com.exe201.opalwed.repository.InformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

}
