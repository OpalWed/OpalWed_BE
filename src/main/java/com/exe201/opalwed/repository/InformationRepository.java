package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InformationRepository extends JpaRepository<Information, Long> {

    Optional<Information> getInformationByAccountEmail(String email);

}
