package com.exe201.opalwed.service;

import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.Product;
import com.exe201.opalwed.model.WeddingConcept;

public interface ProductService {

    //ResponseObject getAllProducts();
    ResponseObject getProductsByBudgetAndConcept(BudgetLevel budgetLevel, WeddingConcept weddingConcept);
    ResponseObject getProductById(Long id);
//    ResponseObject addProduct(ProductDTO product);
//    ResponseObject updateProduct(ProductDTO product);
//    void deleteProduct(Long id);


}
