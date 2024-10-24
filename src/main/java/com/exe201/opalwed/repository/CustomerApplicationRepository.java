package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.CustomerApplication;
import com.exe201.opalwed.model.Information;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerApplicationRepository extends JpaRepository<CustomerApplication,Long> {
    Page<CustomerApplication> getCustomerApplicationsByCustomerInformation(Information information, Pageable pageable);
}
