package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ApplicationDTO;
import com.exe201.opalwed.dto.PaginationResponse;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.CustomerApplication;
import com.exe201.opalwed.model.CustomerApplicationStatus;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.model.PaymentStatus;
import com.exe201.opalwed.repository.CustomerApplicationRepository;
import com.exe201.opalwed.repository.InformationRepository;
import com.exe201.opalwed.service.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.exception.PayOSException;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final CustomerApplicationRepository customerApplicationRepository;

    private final InformationRepository informationRepository;

    @Value("${payment.payos.returnUrl}")
    private String returnUrl;

    @Value("$payment.payos.cancelUrl}")
    private String cancelUrl;

    private final PayOS payOS;


    @Override
    @Transactional
    public ResponseObject createRequest(ApplicationDTO applicationDTO, Authentication authentication) throws Exception {

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
                .price(50000)
                .status(CustomerApplicationStatus.INITIAL)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        customerApplication = customerApplicationRepository.save(customerApplication);
        try{
            PaymentData requestData = getPaymentRequestData(customerApplication);
            CheckoutResponseData data = payOS.createPaymentLink(requestData);
            customerApplication.setPaymentLinkId(data.getPaymentLinkId());
            customerApplicationRepository.save(customerApplication);

            return ResponseObject.builder()
                    .data(data)
                    .isSuccess(true)
                    .message("Yêu cầu của bạn đã được gửi, chúng tôi sẽ liên hệ với bạn sớm nhất có thể!")
                    .status(HttpStatus.OK)
                    .build();
        }catch (PayOSException e){
            throw new OpalException(e.getMessage());
        }
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
                        .price(customerApplication.getPrice())
                        .description(customerApplication.getWeddingDescription())
                        .paymentStatus(customerApplication.getPaymentStatus().name())
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
                        .price(customerApplication.getPrice())
                        .paymentStatus(customerApplication.getPaymentStatus().name())
                        .requiredServicesFile(customerApplication.getRequiredServicesFile())
                        .build())
                .isSuccess(true)
                .message("Lấy thông tin yêu cầu thành công")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject getApplicationsManage(Pageable pagination) {
        Page<ApplicationDTO> applications = customerApplicationRepository.findAll(pagination)
                .map(customerApplication -> ApplicationDTO.builder()
                        .applicationId(customerApplication.getId())
                        .userId(customerApplication.getCustomerInformation().getId())
                        .fullName(customerApplication.getCustomerInformation().getFullName())
                        .createdDate(customerApplication.getCreatedDate().toString())
                        .weddingDate(customerApplication.getWeddingDate())
                        .weddingLocation(customerApplication.getWeddingLocation())
                        .numberOfGuests(customerApplication.getNumberOfGuests())
                        .weddingDescription(customerApplication.getWeddingDescription())
                        .status(customerApplication.getStatus().toString())
                        .price(customerApplication.getPrice())
                        .paymentStatus(customerApplication.getPaymentStatus().name())
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
    public ResponseObject getPaidApplicationsManage(Pageable pagination) {
        Page<ApplicationDTO> applications = customerApplicationRepository.getAllByPaymentStatus(PaymentStatus.PAID, pagination)
                .map(customerApplication -> ApplicationDTO.builder()
                        .applicationId(customerApplication.getId())
                        .userId(customerApplication.getCustomerInformation().getId())
                        .fullName(customerApplication.getCustomerInformation().getFullName())
                        .createdDate(customerApplication.getCreatedDate().toString())
                        .weddingDate(customerApplication.getWeddingDate())
                        .weddingLocation(customerApplication.getWeddingLocation())
                        .numberOfGuests(customerApplication.getNumberOfGuests())
                        .weddingDescription(customerApplication.getWeddingDescription())
                        .status(customerApplication.getStatus().toString())
                        .paymentStatus(customerApplication.getPaymentStatus().name())
                        .price(customerApplication.getPrice())
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
    public ResponseObject getApplicationsByDate(Pageable pagination, LocalDateTime fromDate, LocalDateTime toDate) {
        Page<ApplicationDTO> applications = customerApplicationRepository.getCustomerApplicationsByCreatedDateBetween(fromDate, toDate, pagination)
                .map(customerApplication -> ApplicationDTO.builder()
                        .applicationId(customerApplication.getId())
                        .userId(customerApplication.getCustomerInformation().getId())
                        .fullName(customerApplication.getCustomerInformation().getFullName())
                        .createdDate(customerApplication.getCreatedDate().toString())
                        .weddingDate(customerApplication.getWeddingDate())
                        .weddingLocation(customerApplication.getWeddingLocation())
                        .numberOfGuests(customerApplication.getNumberOfGuests())
                        .weddingDescription(customerApplication.getWeddingDescription())
                        .status(customerApplication.getStatus().toString())
                        .requiredServicesFile(customerApplication.getRequiredServicesFile())
                        .price(customerApplication.getPrice())
                        .description(customerApplication.getWeddingDescription())
                        .paymentStatus(customerApplication.getPaymentStatus().name())
                        .build());
        return ResponseObject.builder()
                .data(new PaginationResponse<>(applications))
                .isSuccess(true)
                .message("Lấy danh sách yêu cầu thành công")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject updatePaymentStatus(Long id, PaymentStatus paymentStatus) {
        CustomerApplication customerApplication = customerApplicationRepository.findById(id)
                .orElseThrow(() -> new OpalException("Không tìm thấy yêu cầu"));
        customerApplication.setPaymentStatus(paymentStatus);

        customerApplicationRepository.save(customerApplication);

        return ResponseObject.builder()
                .isSuccess(true)
                .data(null)
                .message("Cập nhật trạng thái thanh toán thành công")
                .status(HttpStatus.OK)
                .build();
    }

    private PaymentData getPaymentRequestData(CustomerApplication application) {
        final String productName = "Dịch vụ OpalWed";
        final String description = "Dịch vụ tư vấn";

        long orderCode = application.getId();
        ItemData item = ItemData.builder()
                .name(productName)
                .price(application.getPrice())
                .quantity(1)
                .build();
        return PaymentData.builder()
                .orderCode(orderCode)
                .description(description)
                .amount(application.getPrice())
                .item(item)
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .build();
    }
}
