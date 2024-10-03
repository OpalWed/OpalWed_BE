package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.OTPInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPInformationRepository extends JpaRepository<OTPInformation, Long> {
}
