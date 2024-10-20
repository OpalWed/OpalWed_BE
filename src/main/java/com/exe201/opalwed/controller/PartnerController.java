package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.PartnerInformationDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.PartnerStatus;
import com.exe201.opalwed.model.ProductStatus;
import com.exe201.opalwed.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            description = "Create new partner with information. UTILITIES: RESTAURANT,\n" +
                    "    CLOTHES,\n" +
                    "    MAKEUP,\n" +
                    "    ACCESSORIES,\n" +
                    "    PHOTOGRAPHY,\n" +
                    "    DECORATION,\n" +
                    "    INVITATION,")
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

    @Operation(summary = "Update status",description = "Update status of partner: PENDING, ACTIVE, INACTIVE. send partnerId, Pending là cho nó k bị nhảy data ở db")
    @PutMapping("/update-status/{id}")
    public ResponseEntity<ResponseObject> updatePartnerStatus(@PathVariable Long id) {
        ResponseObject responseObject = partnerService.updatePartnerStatus(id);
        return ResponseEntity.ok().body(responseObject);
    }

    @Operation(summary = "Get partner by id",description = "Get partner by id")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getPartnerById(@PathVariable Long id) {
        ResponseObject responseObject = partnerService.getPartnerById(id);
        return ResponseEntity.ok().body(responseObject);
    }

    @Operation(summary = "Get all partners",description = "Get all partners")
    @GetMapping
    public ResponseEntity<ResponseObject> getAllPartners(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String status,
                                                         @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination) {
        PartnerStatus statusEnum = (status != null) ? PartnerStatus.valueOf(status) : null;
        ResponseObject responseObject = partnerService.getAllPartners(name, statusEnum, pagination);
        return ResponseEntity.ok().body(responseObject);
    }



}
