package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.*;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.*;
import com.exe201.opalwed.repository.PartnerRepository;
import com.exe201.opalwed.service.PartnerService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
                .partnerName(partner.getPartnerName())
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
                .partnerName(req.getPartnerName())
                .partnerInformation(information)
                .note(req.getNote())
                .successEvent(0)
                .status(PartnerStatus.ACTIVE)
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

    @Override
    public ResponseObject getPartnerById(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(()-> new OpalException("Partner không tồn tại!"));
        return ResponseObject.builder()
                .message("Lấy thông tin partner thành công!")
                .isSuccess(true)
                .data(mapEntityToDTO(partner))
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @Transactional
    public ResponseObject updatePartner(PartnerInformationDTO req) {

        Partner partner = partnerRepository.findById(req.getPartnerId()).orElseThrow(()-> new OpalException("Partner không tồn tại!"));
        partner.getPartnerInformation().setFullName(req.getFullName());
        partner.getPartnerInformation().setPhone(req.getPhone());
        partner.getPartnerInformation().setAddress(req.getAddress());
        partner.getPartnerInformation().setDescription(req.getDescription());
        partner.getPartnerInformation().setImageUrl(req.getImageUrl());
        partner.setPartnerName(req.getPartnerName());
        partner.setSuccessEvent(req.getSuccessEvent());
        partner.setNote(req.getNote());
        partner.setStatus(PartnerStatus.valueOf(req.getStatus()));

        if (req.getUtilities() != null) {
            // Get the current utilities from the partner
            Set<PartnerUtility> currentUtilities = partner.getUtilities();

            // Create a new Set for updated utilities
            Set<PartnerUtility> updatedUtilities = new HashSet<>();

            // Add all new utilities from the request
            for (PartnerUtilityDTO dto : req.getUtilities()) {
                PartnerUtility newUtility = new PartnerUtility(UtilityType.valueOf(dto.getUtilityType()));
                newUtility.setPartner(partner); // Ensure bidirectional mapping
                updatedUtilities.add(newUtility);
            }

            // Remove outdated utilities
            currentUtilities.removeIf(existingUtility ->
                    updatedUtilities.stream().noneMatch(newUtility ->
                            newUtility.getUtilityType().equals(existingUtility.getUtilityType()))
            );

            // Add new utilities that aren't already in the current set
            updatedUtilities.forEach(newUtility -> {
                if (currentUtilities.stream().noneMatch(existingUtility ->
                        existingUtility.getUtilityType().equals(newUtility.getUtilityType()))) {
                    currentUtilities.add(newUtility); // Add the new utility
                }
            });

            // Set the updated utilities back to the partner
            partner.setUtilities(currentUtilities);
        }

        // Update images if provided
        if (req.getImages() != null) {
            // Get the current images from the partner
            Set<PartnerImage> currentImages = partner.getImages();

            // Create a new Set for updated images
            Set<PartnerImage> updatedImages = new HashSet<>();

            // Add all new images from the request
            for (PartnerImageDTO dto : req.getImages()) {
                PartnerImage newImage = new PartnerImage(dto.getUrl());
                newImage.setPartner(partner); // Ensure bidirectional mapping
                updatedImages.add(newImage);
            }
            // Remove outdated images
            currentImages.removeIf(existingImage ->
                    updatedImages.stream().noneMatch(newImage ->
                            newImage.getUrl().equals(existingImage.getUrl()))
            );

            // Add new images that aren't already in the current set
            updatedImages.forEach(newImage -> {
                if (currentImages.stream().noneMatch(existingImage ->
                        existingImage.getUrl().equals(newImage.getUrl()))) {
                    currentImages.add(newImage); // Add the new image
                }
            });
            // Set the updated images back to the partner
            partner.setImages(currentImages);
        }
        partner = partnerRepository.save(partner);
        return ResponseObject.builder()
                .message("Cập nhật partner thành công!")
                .isSuccess(true)
                .data(mapEntityToDTO(partner))
                .build();
    }
    @Override
    public ResponseObject updatePartnerStatus(Long partnerId) {
        Partner partner = partnerRepository.findById(partnerId).orElseThrow(()-> new OpalException("Không tìm thấy partner!"));
        if(partner.getStatus().equals(PartnerStatus.ACTIVE)){
            partner.setStatus(PartnerStatus.INACTIVE);
        }
        else {
            partner.setStatus(PartnerStatus.ACTIVE);
        }
        partner = partnerRepository.save(partner);
        return ResponseObject.builder()
                .message("Cập nhật trạng thái partner thành công!")
                .isSuccess(true)
                .data(mapEntityToDTO(partner))
                .build();
    }

    @Override
    public ResponseObject getAllPartners(String name, PartnerStatus status, Pageable pageable) {

        Specification<Partner> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by name
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("partnerName")), "%" + name.toLowerCase() + "%"));
            }
            // Filter by ProductStatus
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
        Page<PartnerInformationDTO> partners = partnerRepository.findAll(spec, pageable).map(this::mapEntityToDTO);

        return ResponseObject.builder()
                .message("Lấy danh sách partner thành công!")
                .isSuccess(true)
                .data(new PaginationResponse<>(partners))
                .build();



    }


}
