package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.repository.CustomerApplicationRepository;
import com.exe201.opalwed.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    @Override
    public ResponseObject getApplicationCountData(int year) {
        List<Object[]> results = customerApplicationRepository.countApplicationsByMonthInYear(year);
        Map<Integer, Long> monthlyCounts = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            monthlyCounts.put(month, 0L);
        }

        for (Object[] row : results) {
            Integer month = (Integer) row[0];
            Long count = (Long) row[1];
            monthlyCounts.put(month, count);
        }

        return ResponseObject.builder()
                .data(monthlyCounts)
                .status(HttpStatus.OK)
                .isSuccess(true)
                .message("Số lượng đơn theo tháng trong năm " + year)
                .build();



    }

    private final CustomerApplicationRepository customerApplicationRepository;






}
