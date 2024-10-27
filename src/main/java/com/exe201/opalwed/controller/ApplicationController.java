package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ApplicationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.PaymentStatus;
import com.exe201.opalwed.service.CustomerApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final CustomerApplicationService customerApplicationService;

    @Operation(summary = "Create new application",
            description = "{</br>" +
                    "  \"weddingDate\": \"yyyy-MM-ddTHH:mm:ss\",</br>" +
                    "  \"weddingLocation\": \"New York City\",</br>" +
                    "  \"numberOfGuests\": x, (1<x<1000) </br>" +
                    "  \"weddingDescription\": \"Biggest Wedding ever\",</br>" +
                    "  \"requiredServicesFile\": \"This is file of the requirement\"</br>" +
                    "}")
    @PostMapping
    public ResponseEntity<ResponseObject> createApplication(
            @Valid @RequestBody ApplicationDTO applicationDTO,
            Authentication authentication) throws Exception {
        ResponseObject responseObject = customerApplicationService.createRequest(applicationDTO, authentication);
        return ResponseEntity.ok().body(responseObject);
    }


    @GetMapping
    public ResponseEntity<ResponseObject> getApplications(Authentication authentication,
                                                          @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination) {
        ResponseObject responseObject = customerApplicationService.getApplications(authentication, pagination);
        return ResponseEntity.ok().body(responseObject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getApplicationById(@PathVariable Long id) {

        ResponseObject responseObject = customerApplicationService.getApplicationById(id);
        return ResponseEntity.ok().body(responseObject);
    }

    @PutMapping("/{id}/{paymentStatus}")
    public void updateApplicationStatus(@PathVariable Long id, @PathVariable PaymentStatus paymentStatus) {
        customerApplicationService.updatePaymentStatus(id, paymentStatus);
    }

}
