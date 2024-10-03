package com.exe201.opalwed.controller;


import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.WeddingConcept;
import com.exe201.opalwed.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    @Operation(summary = "Get products by budget and concept",
            description = "CONCEPT: TRADITIONAL, MODERN, MINIMALISM, VINTAGE" +
                    "BUDGET LEVEL: LOW, MEDIUM, HIGH, VERY_HIGH")
    public ResponseEntity<ResponseObject> getProductsByBudgetAndConcept(@RequestParam String budgetLevel, @RequestParam String weddingConcept) {
        ResponseObject responseObject = productService.getProductsByBudgetAndConcept(BudgetLevel.valueOf(budgetLevel), WeddingConcept.valueOf(weddingConcept));
        return ResponseEntity.ok().body(responseObject);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        ResponseObject responseObject = productService.getProductById(id);
        return ResponseEntity.ok().body(responseObject);
    }

}
