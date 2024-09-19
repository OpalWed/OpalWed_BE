package com.exe201.opalwed.service;

import com.exe201.opalwed.model.dto.request.CustomerApplicationRequest;
import com.exe201.opalwed.model.dto.response.CustomerApplicationResponse;
import org.springframework.data.domain.Page;

public interface CustomerApplicationService {

    CustomerApplicationResponse createCustomerApplication(CustomerApplicationRequest request);
    CustomerApplicationResponse getCustomerApplication(Long id);
    Page<CustomerApplicationResponse> getCustomerApplications(int page, int size, String sortBy, String sortDirection);
    CustomerApplicationRequest updateCustomerApplication(Long id, CustomerApplicationRequest request);


}
