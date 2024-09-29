package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ChangeProfileRequest;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.AccountInformationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/accountInfo")
@RequiredArgsConstructor
public class AccountInformationController {

    private final AccountInformationService service;

    @GetMapping("/accounts")
    @Operation(description = "Admin role: get all account")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseObject listAllAccount() {
        return service.getAllAccounts();
    }

    @GetMapping("/infos")
    @Operation(description = "Admin role: get all information + account")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseObject listAllInfos() {
        return service.getAllInfos();
    }

    @GetMapping("/info/{id}")
    @Operation(description = "Admin role: get information của user bằng information id")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseObject getInfoByUserId(@PathVariable Long id) {
        return service.getAccountInfoByUserId(id);
    }

    @GetMapping("/info")
    @Operation(description = "User role: get information của user bằng jwt token trong header")
    public ResponseObject getCurrentUserInfo(Authentication authentication) {
        return service.getCurrentUserInfo(authentication);
    }

    @PutMapping("/update")
    @Operation(summary = "Update profile",description = "Update profile of user, đừng để field nào bị trống, nào không đổi thì truyền data cũ vào")
    public ResponseObject updateAccount(@Valid @RequestBody ChangeProfileRequest request, Authentication authentication) {
        return service.updateAccount(authentication,request);
    }





}
