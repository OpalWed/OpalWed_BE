package com.exe201.opalwed.service;

import com.exe201.opalwed.model.dto.response.PartnerApplicationResponse;
import org.springframework.data.domain.Page;

public interface PartnerApplicationService {

    PartnerApplicationResponse createPartnerApplication();
    Page<PartnerApplicationResponse> getPartnerApplications(int page, int size, String sortBy, String sortDirection);
    PartnerApplicationResponse getPartnerApplication(Long id);
    PartnerApplicationResponse updatePartnerApplication(Long id, PartnerApplicationResponse request);

}
