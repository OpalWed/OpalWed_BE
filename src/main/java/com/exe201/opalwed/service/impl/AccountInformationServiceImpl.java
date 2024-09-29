package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ChangeProfileRequest;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.Account;
import com.exe201.opalwed.model.AccountStatus;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.repository.AccountRepository;
import com.exe201.opalwed.repository.InformationRepository;
import com.exe201.opalwed.service.AccountInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountInformationServiceImpl implements AccountInformationService {

    private final AccountRepository accountRepo;
    private final InformationRepository infoRepo;


    @Override
    public ResponseObject getAllAccounts() {
        List<Account> data = accountRepo.findAll();
        data.forEach(x -> x.setPassword(""));

        return ResponseObject.builder()
                .data(data)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Danh sách tài khoản")
                .build();
    }

    @Override
    public ResponseObject getAllInfos() {
        List<Information> data = infoRepo.findAll();
        data.forEach(x -> {
            if (x.getAccount() != null) {
                x.getAccount().setPassword("");
            }
        });

        return ResponseObject.builder()
                .data(data)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Danh sách thông tin và tài khoản")
                .build();
    }

    @Override
    public ResponseObject getCurrentUserInfo(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new OpalException("Yêu cầu đăng nhập");
        }
        Information information = infoRepo.getInformationByAccountEmail(authentication.getName())
                .orElseThrow(() -> new OpalException("Yêu cầu đăng nhập"));
        information.getAccount().setPassword("");

        return ResponseObject.builder()
                .data(information)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Thông tin cá nhân")
                .build();
    }

    @Override
    public ResponseObject getAccountInfoByUserId(Long id) {
        Information info = infoRepo.findById(id).orElseThrow(() -> new OpalException("ID không tồn tại"));
        if (info.getAccount() != null) {
            info.getAccount().setPassword("");
        }

        return ResponseObject.builder()
                .data(info)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Thông tin cá nhân")
                .build();
    }

    @Override
    public ResponseObject updateAccount(Authentication authentication, ChangeProfileRequest request) {

        if (authentication == null || authentication.getName() == null) {
            throw new OpalException("Yêu cầu đăng nhập");
        }
        Information information = infoRepo.getInformationByAccountEmail(authentication.getName())
                .orElseThrow(() -> new OpalException("Yêu cầu đăng nhập"));

        information.setFullName(request.getFullName());
        information.setPhone(request.getPhone());
        information.setAddress(request.getAddress());
        information.setDescription(request.getDescription());
        information.setImageUrl(request.getImageUrl());

        information = infoRepo.save(information);

        information.getAccount().setPassword("");

        return ResponseObject.builder()
                .data(information)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Thay đổi thông tin cá nhân thành công!")
                .build();
    }

    @Override
    public ResponseObject updateAccountStatus(Long id, AccountStatus status) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new OpalException(String.format("Không tồn tại ID: %s", id)));
        account.setStatus(status);
        account = accountRepo.save(account);
        account.setPassword("");

        return ResponseObject.builder()
                .data(account)
                .isSuccess(true)
                .status(HttpStatus.OK)
                .message("Thay đổi trạng thái tài khoản thành công")
                .build();
    }
}
