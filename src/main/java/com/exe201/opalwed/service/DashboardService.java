package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ResponseObject;

import java.time.LocalDateTime;

public interface DashboardService {

    ResponseObject getApplicationCountData(int year);

    ResponseObject getTotalCountUserRevenueApplication();

    ResponseObject getDailyRevenueForYear(int year);
}
