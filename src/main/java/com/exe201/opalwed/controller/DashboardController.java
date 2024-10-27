package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("${api.endpoint.base-url}/manage/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ResponseObject> getApplicationCountData(
            @RequestParam int year

    ) {
        ResponseObject responseObject = dashboardService.getApplicationCountData(year);
        return ResponseEntity.ok().body(responseObject);
    }

}
