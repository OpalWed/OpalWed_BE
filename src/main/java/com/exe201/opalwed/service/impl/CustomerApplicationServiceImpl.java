package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ApplicationDTO;
import com.exe201.opalwed.dto.PaginationResponse;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.CustomerApplication;
import com.exe201.opalwed.model.CustomerApplicationStatus;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.repository.CustomerApplicationRepository;
import com.exe201.opalwed.repository.InformationRepository;
import com.exe201.opalwed.service.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final CustomerApplicationRepository customerApplicationRepository;

    private final InformationRepository informationRepository;
    @Override
    public ResponseObject createRequest(ApplicationDTO applicationDTO, Authentication authentication) {

        Information information = informationRepository.getInformationByAccountEmail(authentication.getName())
                .orElseThrow(() -> new OpalException("Yêu cầu đăng nhập"));

        CustomerApplication customerApplication = CustomerApplication.builder()
                .createdDate(LocalDateTime.now())
                .customerInformation(information)
                .requiredServicesFile(applicationDTO.getRequiredServicesFile())
                .weddingDate(applicationDTO.getWeddingDate())
                .weddingDescription(applicationDTO.getWeddingDescription())
                .weddingLocation(applicationDTO.getWeddingLocation())
                .numberOfGuests(applicationDTO.getNumberOfGuests())
                .status(CustomerApplicationStatus.INITIAL)
                .build();

        customerApplicationRepository.save(customerApplication);

        return ResponseObject.builder()
                .data(null)
                .isSuccess(true)
                .message("Yêu cầu của bạn đã được gửi, chúng tôi sẽ liên hệ với bạn sớm nhất có thể!")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject getApplications(Authentication authentication, Pageable pagination) {
        Information information = informationRepository.getInformationByAccountEmail(authentication.getName())
                .orElseThrow(() -> new OpalException("Yêu cầu đăng nhập"));

        Page<ApplicationDTO> applications = customerApplicationRepository.getCustomerApplicationsByCustomerInformation(information, pagination)
                .map(customerApplication -> ApplicationDTO.builder()
                        .applicationId(customerApplication.getId())

                        .createdDate(customerApplication.getCreatedDate().toString())
                        .weddingDate(customerApplication.getWeddingDate())
                        .weddingLocation(customerApplication.getWeddingLocation())
                        .numberOfGuests(customerApplication.getNumberOfGuests())
                        .weddingDescription(customerApplication.getWeddingDescription())
                        .status(customerApplication.getStatus().toString())
                        .requiredServicesFile(customerApplication.getRequiredServicesFile())
                        .build());

        return ResponseObject.builder()
                .data(new PaginationResponse<>(applications))
                .isSuccess(true)
                .message("Lấy danh sách yêu cầu thành công")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject getApplicationById(Long id) {
        CustomerApplication customerApplication = customerApplicationRepository.findById(id)
                .orElseThrow(() -> new OpalException("Không tìm thấy yêu cầu"));

        return ResponseObject.builder()
                .data(ApplicationDTO.builder()
                        .applicationId(customerApplication.getId())

                        .createdDate(customerApplication.getCreatedDate().toString())
                        .weddingDate(customerApplication.getWeddingDate())
                        .weddingLocation(customerApplication.getWeddingLocation())
                        .numberOfGuests(customerApplication.getNumberOfGuests())
                        .weddingDescription(customerApplication.getWeddingDescription())
                        .status(customerApplication.getStatus().toString())
                        .requiredServicesFile(customerApplication.getRequiredServicesFile())
                        .build())
                .isSuccess(true)
                .message("Lấy thông tin yêu cầu thành công")
                .status(HttpStatus.OK)
                .build();
    }
}
