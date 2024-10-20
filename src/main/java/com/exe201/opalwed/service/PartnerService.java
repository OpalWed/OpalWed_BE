package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import org.apache.coyote.Response;

public interface PartnerService {
    PartnerInformationDTO connectNewPartner(PartnerInformationDTO partnerInformation);

    ResponseObject getPartnerById(Long partnerId);
    ResponseObject updatePartner(PartnerInformationDTO partnerInformation);

    ResponseObject updatePartnerStatus(Long partnerId);
}
