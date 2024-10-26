package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/manage/application")
@RequiredArgsConstructor
public class ManageApplicationController {


    private final CustomerApplicationService customerApplicationService;

    @GetMapping
    public ResponseEntity<ResponseObject> getApplications(@PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination) {
        ResponseObject responseObject = customerApplicationService.getApplicationsManage(pagination);
        return ResponseEntity.ok().body(responseObject);
    }

}
