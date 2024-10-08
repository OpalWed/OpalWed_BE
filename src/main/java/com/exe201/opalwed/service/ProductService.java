package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.Product;
import com.exe201.opalwed.model.UtilityType;
import com.exe201.opalwed.model.WeddingConcept;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    //ResponseObject getAllProducts();
    ResponseObject getProductsByBudgetAndConceptAndUtility(BudgetLevel budgetLevel, WeddingConcept weddingConcept, UtilityType utilityType, Pageable pageable);
    ResponseObject getProductById(Long id);

    ResponseObject getProductByUtilityType(UtilityType utilityType, Pageable pageable);

    ResponseObject addProduct(ProductDTO product);
    ResponseObject updateProduct(ProductDTO product);
    ResponseObject deleteProduct(Long id);


}
