package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByEmail(String email);

    boolean existsAccountByEmail(String email);


}
