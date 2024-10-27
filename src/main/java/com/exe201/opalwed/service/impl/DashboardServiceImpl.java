package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.repository.AccountRepository;
import com.exe201.opalwed.repository.CustomerApplicationRepository;
import com.exe201.opalwed.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CustomerApplicationRepository customerApplicationRepository;
    private final AccountRepository accountRepository;
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
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", "Tháng " + month);
            monthData.put("application", monthlyCounts.get(month));
            formattedResults.add(monthData);
        }


        return ResponseObject.builder()
                .data(formattedResults)
                .status(HttpStatus.OK)
                .isSuccess(true)
                .message("Số lượng đơn theo tháng trong năm " + year)
                .build();
    }

    @Override
    public ResponseObject getTotalCountUserRevenueApplication() {

        LocalDateTime now = LocalDateTime.now();
        Optional<Object[]> result = customerApplicationRepository.countTotalAndSumPriceForCurrentMonth(now);

        Map<String, Long> resultMap = new HashMap<>();
        resultMap.put("application-count", 0L);
        resultMap.put("sum-revenue", 0L);
        resultMap.put("user-count", accountRepository.count());

        if (result.isPresent()) {
            Object[] data = result.get();
            if (data.length > 0) {
                Object[] innerArray = (Object[]) data[0];
                if (innerArray.length >= 2) {
                    resultMap.put("application-count", (Long) innerArray[0]);
                    resultMap.put("sum-revenue", (Long) innerArray[1]);
                }
            }
        }
        return ResponseObject.builder()
                .data(resultMap)
                .status(HttpStatus.OK)
                .isSuccess(true)
                .message("Tổng số đơn, tổng doanh thu của tháng hiện tại và tổng số người dùng")
                .build();
    }

    @Override
    public ResponseObject getDailyRevenueForYear(int year) {
        List<Object[]> results = customerApplicationRepository.findDailyRevenueByYear(year);
        List<Map<String, Object>> dailyRevenueList = new ArrayList<>();
        for (Object[] result : results) {
            LocalDate date = (LocalDate) result[0];
            Long totalRevenue = (Long) result[1];
            Map<String, Object> revenueEntry = new HashMap<>();
            revenueEntry.put("date", date.toString());
            revenueEntry.put("revenue", totalRevenue);
            dailyRevenueList.add(revenueEntry);
        }
        return ResponseObject.builder()
                .data(dailyRevenueList)
                .status(HttpStatus.OK)
                .isSuccess(true)
                .message("Doanh thu hàng ngày trong năm " + year)
                .build();

    }


}
