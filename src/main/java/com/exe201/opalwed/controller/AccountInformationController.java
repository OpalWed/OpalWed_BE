package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.AccountInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/accountInfo")
@RequiredArgsConstructor
public class AccountInformationController {

    private final AccountInformationService service;

    @GetMapping("/accounts")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseObject listAllAccount() {
        return service.getAllAccounts();
    }

    @GetMapping("/infos")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseObject listAllInfos() {
        return service.getAllInfos();
    }

    @GetMapping("/info")
    public ResponseObject getCurrentUserInfo(Authentication authentication) {
        return service.getCurrentUserInfo(authentication);
    }




}
