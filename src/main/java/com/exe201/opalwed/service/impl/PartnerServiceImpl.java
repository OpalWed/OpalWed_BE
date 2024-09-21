package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.PartnerImageDTO;
import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.PartnerUtilityDTO;
import com.exe201.opalwed.model.*;
import com.exe201.opalwed.repository.PartnerRepository;
import com.exe201.opalwed.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;


    private PartnerInformationDTO mapEntityToDTO(Partner partner) {
        PartnerInformationDTO dto = PartnerInformationDTO.builder()
                .informationId(partner.getPartnerInformation().getId())
                .fullName(partner.getPartnerInformation().getFullName())
                .phone(partner.getPartnerInformation().getPhone())
                .address(partner.getPartnerInformation().getAddress())
                .description(partner.getPartnerInformation().getDescription())
                .imageUrl(partner.getPartnerInformation().getImageUrl())
                .partnerId(partner.getId())
                .status(partner.getStatus().name())
                .successEvent(partner.getSuccessEvent())
                .note(partner.getNote())
                .build();
        if (partner.getUtilities() != null) {
            dto.setUtilities(partner.getUtilities().stream()
                    .map(x -> new PartnerUtilityDTO(x.getId(), x.getUtilityType().name()))
                    .collect(Collectors.toSet()));
        }
        if (partner.getImages() != null) {
            dto.setImages(partner.getImages().stream().map(x -> new PartnerImageDTO(x.getId(), x.getUrl())).collect(Collectors.toSet()));
        }
        return dto;
    }

    @Override
    @Transactional
    public PartnerInformationDTO connectNewPartner(PartnerInformationDTO req) {

        Information information = Information.builder()
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .address(req.getAddress())
                .imageUrl(req.getImageUrl())
                .build();

        Partner partner = Partner.builder()
                .partnerInformation(information)
                .note(req.getNote())
                .successEvent(0)
                .status(PartnerStatus.APPROVED)
                .build();

        if (req.getUtilities() != null) {
            Set<PartnerUtility> utilities = req.getUtilities().stream()
                    .map(x -> new PartnerUtility(UtilityType.valueOf(x.getUtilityType())))
                    .collect(Collectors.toSet());
            partner.setUtilities(utilities);
        }
        if (req.getImages() != null) {
            partner.setImages(req.getImages().stream().map(x -> new PartnerImage(x.getUrl())).collect(Collectors.toSet()));
        }

        partner = partnerRepository.save(partner);

        return mapEntityToDTO(partner);
    }
}
