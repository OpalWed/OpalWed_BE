package com.exe201.opalwed.repository;

import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.Product;
import com.exe201.opalwed.model.WeddingConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByBudgetLevelAndWeddingConcept(BudgetLevel budgetLevel, WeddingConcept weddingConcept);

}
