package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.PartnerStatus;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;

public interface PartnerService {
    PartnerInformationDTO connectNewPartner(PartnerInformationDTO partnerInformation);

    ResponseObject getPartnerById(Long partnerId);
    ResponseObject updatePartner(PartnerInformationDTO partnerInformation);

    ResponseObject updatePartnerStatus(Long partnerId);

    ResponseObject getAllPartners(String name, PartnerStatus status, Pageable pageable);
}
