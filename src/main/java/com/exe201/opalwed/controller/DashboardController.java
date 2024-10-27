package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/total")
    public ResponseEntity<ResponseObject> getTotalCount() {
        ResponseObject responseObject = dashboardService.getTotalCountUserRevenueApplication();
        return ResponseEntity.ok().body(responseObject);
    }
    @GetMapping("/daily/{year}")
    public ResponseEntity<ResponseObject> getDailyRevenue(@PathVariable int year) {
        ResponseObject responseObject = dashboardService.getDailyRevenueForYear(year);
        return ResponseEntity.ok().body(responseObject);
    }

}
