package com.exe201.opalwed.controller;

import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.ProductStatus;
import com.exe201.opalwed.model.UtilityType;
import com.exe201.opalwed.model.WeddingConcept;
import com.exe201.opalwed.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.endpoint.base-url}/manage/product")
@RequiredArgsConstructor
public class ManageProductController {


    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllProducts(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String budgetLevel,
                                                         @RequestParam(required = false) String weddingConcept,
                                                         @RequestParam(required = false) String utility,
                                                         @RequestParam(required = false) String status,
                                                         @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination) {
        BudgetLevel budgetLevelEnum = (budgetLevel != null) ? BudgetLevel.valueOf(budgetLevel) : null;
        WeddingConcept weddingConceptEnum = (weddingConcept != null) ? WeddingConcept.valueOf(weddingConcept) : null;
        UtilityType utilityEnum = (utility != null) ? UtilityType.valueOf(utility) : null;
        ProductStatus statusEnum = (status != null) ? ProductStatus.valueOf(status) : null;

        ResponseObject responseObject = productService.getAllProducts(
                name,
                budgetLevelEnum,
                weddingConceptEnum,
                utilityEnum,
                statusEnum,
                pagination);
        return ResponseEntity.ok().body(responseObject);
    }
    @Operation(summary = "Create new product",
            description = "Create new partner product with information.</br>" +
                    "CONCEPT: TRADITIONAL, MODERN, MINIMALISM, VINTAGE </br> " +
                    "BUDGET LEVEL: LOW, MEDIUM, HIGH, VERY_HIGH </br> " +
                    "UTILITY TYPE: RESTAURANT, CLOTHES, MAKEUP, JEWELRY </br> " +
                    "Status: AVAILABLE, UNAVAILABLE, DELETED")
    @PostMapping
    public ResponseEntity<ResponseObject> addProduct(@RequestBody ProductDTO request) {
        ResponseObject responseObject = productService.addProduct(request);
        return ResponseEntity.ok().body(responseObject);
    }

    @PutMapping
    public ResponseEntity<ResponseObject> updateProduct(@RequestBody ProductDTO request) {
        ResponseObject responseObject = productService.updateProduct(request);
        return ResponseEntity.ok().body(responseObject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        ResponseObject responseObject = productService.deleteProduct(id);
        return ResponseEntity.ok().body(responseObject);
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<ResponseObject> changeProductStatus(@PathVariable Long id) {
        ResponseObject responseObject = productService.changeProductStatus(id);
        return ResponseEntity.ok().body(responseObject);
    }



}
