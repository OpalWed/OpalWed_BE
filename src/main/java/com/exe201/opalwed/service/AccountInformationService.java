package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ResponseObject;
import org.springframework.security.core.Authentication;

public interface AccountInformationService {
    ResponseObject getAllAccounts();

    ResponseObject getAllInfos();

    ResponseObject getCurrentUserInfo(Authentication authentication);
}
