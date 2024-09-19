package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.PartnerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerApplicationRepository extends JpaRepository<PartnerApplication, Long> {
}
