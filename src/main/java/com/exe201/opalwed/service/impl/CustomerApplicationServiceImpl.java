package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.model.dto.request.CustomerApplicationRequest;
import com.exe201.opalwed.model.dto.response.CustomerApplicationResponse;
import com.exe201.opalwed.service.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    @Override
    public CustomerApplicationResponse createCustomerApplication(CustomerApplicationRequest request) {
        return null;
    }

    @Override
    public CustomerApplicationResponse getCustomerApplication(Long id) {
        return null;
    }

    @Override
    public Page<CustomerApplicationResponse> getCustomerApplications(int page, int size, String sortBy, String sortDirection) {
        return null;
    }

    @Override
    public CustomerApplicationRequest updateCustomerApplication(Long id, CustomerApplicationRequest request) {
        return null;
    }
}
