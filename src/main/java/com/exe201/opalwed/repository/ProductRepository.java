package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findByBudgetLevelAndWeddingConceptAndStatusAndUtility(BudgetLevel budgetLevel, WeddingConcept weddingConcept, ProductStatus status,UtilityType utilityType, Pageable pageable);
    Page<Product> findByUtilityAndStatus(UtilityType utilityType, ProductStatus status, Pageable pageable);
    Page<Product> findByPartnerId(Long partnerId, Pageable pageable);

}
