package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.CustomerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerApplicationRepository extends JpaRepository<CustomerApplication,Integer> {



}
