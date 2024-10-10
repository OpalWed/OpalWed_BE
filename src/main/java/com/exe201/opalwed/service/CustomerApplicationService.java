package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ApplicationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import org.springframework.security.core.Authentication;

public interface CustomerApplicationService {

    ResponseObject createRequest(ApplicationDTO applicationDTO, Authentication authentication);

}