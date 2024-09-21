package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;


    @PostMapping("/new")
    public PartnerInformationDTO connectNewPartner(@Valid @RequestBody PartnerInformationDTO partnerInformation) {
        return partnerService.connectNewPartner(partnerInformation);
    }



}
