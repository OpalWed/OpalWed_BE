package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/partner")
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;


    @PostMapping("/new")
    @Operation(summary = "Create new partner",
            description = "Create new partner with information. UTILITIES: RESTAURANT, CLOTHES, MAKEKUP, JEWELRY")
    public ResponseEntity<ResponseObject> connectNewPartner(@Valid @RequestBody PartnerInformationDTO partnerInformation) {
        PartnerInformationDTO data = partnerService.connectNewPartner(partnerInformation);

        var responseObject = ResponseObject.builder()
                .message("Tạo mới thành công")
                .isSuccess(true)
                .data(data)
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @Operation(summary = "Update information",description = "Dùng lại model của create partner nhe, " +
            "chỉ cần thêm id vào " +
            "(cái nào update thì đổi, còn không thì giữ nguyên, đừng để trống)")
    @PutMapping("/update-info")
    public ResponseEntity<ResponseObject> updatePartner(@Valid @RequestBody PartnerInformationDTO partnerInformation) {
        ResponseObject responseObject = partnerService.updatePartner(partnerInformation);
        return ResponseEntity.ok().body(responseObject);
    }

    @Operation(summary = "Update status",description = "Update status of partner: PENDING, APPROVED, CANCELLED. send partnerId and status")
    @PostMapping("/update-status")
    public ResponseEntity<ResponseObject> updatePartnerStatus(@RequestBody PartnerInformationDTO partnerInformation) {
        ResponseObject responseObject = partnerService.updatePartnerStatus(partnerInformation.getPartnerId(), partnerInformation.getStatus());
        return ResponseEntity.ok().body(responseObject);
    }





}
