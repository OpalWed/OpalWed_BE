package com.exe201.opalwed.repository;


import com.exe201.opalwed.model.CustomerApplication;
import com.exe201.opalwed.model.Information;
import com.exe201.opalwed.model.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerApplicationRepository extends JpaRepository<CustomerApplication,Long> {
    Page<CustomerApplication> getCustomerApplicationsByCustomerInformation(Information information, Pageable pageable);

    Page<CustomerApplication> getAllByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    Page<CustomerApplication> getCustomerApplicationsByCreatedDateBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pagination);

    @Query("SELECT FUNCTION('MONTH', c.createdDate) AS month, COUNT(c) AS count " +
            "FROM CustomerApplication c " +
            "WHERE FUNCTION('YEAR', c.createdDate) = :year " +
            "GROUP BY FUNCTION('MONTH', c.createdDate) " +
            "ORDER BY month")
    List<Object[]> countApplicationsByMonthInYear(@Param("year") int year);

    @Query("SELECT COUNT(c), COALESCE(SUM(CASE WHEN MONTH(c.createdDate) = MONTH(:currentDate) AND YEAR(c.createdDate) = YEAR(:currentDate) THEN c.price ELSE 0 END), 0) " +
            "FROM CustomerApplication c")
    Optional<Object[]> countTotalAndSumPriceForCurrentMonth(@Param("currentDate") LocalDateTime currentDate);


    @Query("SELECT CAST(ca.createdDate AS LocalDate) AS date, SUM(ca.price) AS totalRevenue " +
            "FROM CustomerApplication ca " +
            "WHERE YEAR(ca.createdDate) = :year " +
            "GROUP BY CAST(ca.createdDate AS LocalDate)")
    List<Object[]> findDailyRevenueByYear(@Param("year") int year);
}
