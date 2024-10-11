package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.*;
import org.springframework.data.domain.Pageable;

public interface ProductService {


    ResponseObject getProductsByBudgetAndConceptAndUtility(BudgetLevel budgetLevel, WeddingConcept weddingConcept, UtilityType utilityType, Pageable pageable);
    ResponseObject getProductById(Long id);

    ResponseObject getProductByUtilityType(UtilityType utilityType, Pageable pageable);

    // Manage Product

    ResponseObject getAllProducts(String name, BudgetLevel budgetLevel, WeddingConcept weddingConcept, UtilityType utility, ProductStatus status, Pageable pageable);
    ResponseObject getProductsByPartnerId(Long partnerId, Pageable pageable);
    ResponseObject addProduct(ProductDTO product);
    ResponseObject updateProduct(ProductDTO product);
    ResponseObject deleteProduct(Long id);


}
