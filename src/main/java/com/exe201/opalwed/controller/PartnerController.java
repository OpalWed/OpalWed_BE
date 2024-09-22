package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.PartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseObject> connectNewPartner(@Valid @RequestBody PartnerInformationDTO partnerInformation) {
        PartnerInformationDTO data = partnerService.connectNewPartner(partnerInformation);

        var responseObject = ResponseObject.builder()
                .message("Add new partner successfully!")
                .isSuccess(true)
                .data(data)
                .build();
        return ResponseEntity.ok().body(responseObject);




    }



}
