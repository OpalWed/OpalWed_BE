package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ApplicationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.PaymentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;

public interface CustomerApplicationService {

    ResponseObject createRequest(ApplicationDTO applicationDTO, Authentication authentication) throws Exception;

    ResponseObject getApplications(Authentication authentication, Pageable pagination);

    ResponseObject getApplicationById(Long id);

    ResponseObject getApplicationsManage(Pageable pagination);

    ResponseObject getPaidApplicationsManage(Pageable pagination);

    ResponseObject getApplicationsByDate(Pageable pagination, LocalDateTime fromDate, LocalDateTime toDate);

    void updatePaymentStatus(Long id, PaymentStatus paymentStatus);
}
