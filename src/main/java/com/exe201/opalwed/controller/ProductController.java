package com.exe201.opalwed.controller;


import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.model.BudgetLevel;
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
@RequestMapping("${api.endpoint.base-url}/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping
    @Operation(summary = "Get products by budget and concept",
            description = "CONCEPT: TRADITIONAL,EUROPE, MINIMALISM, VINTAGE </br> " +
                    "BUDGET LEVEL: LOW, MEDIUM, HIGH, PREMIUM </br> " +
                    "UTILITY TYPE: RESTAURANT,\n" +
                    "    CLOTHES,\n" +
                    "    MAKEUP,\n" +
                    "    ACCESSORIES,\n" +
                    "    PHOTOGRAPHY,\n" +
                    "    DECORATION,\n" +
                    "    INVITATION, </br> " +
                    "{</br>" +
                    "  \"page\": 0,</br>" +
                    "  \"size\": 1</br>" +
                    "}" +
                    "</br> paging gửi như này là được")
    public ResponseEntity<ResponseObject> getProductsByBudgetAndConcept(
            @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination,
            @RequestParam String budgetLevel,
            @RequestParam String weddingConcept,
            @RequestParam String utilityType) {
        ResponseObject responseObject = productService.getProductsByBudgetAndConceptAndUtility(BudgetLevel.valueOf(budgetLevel), WeddingConcept.valueOf(weddingConcept), UtilityType.valueOf(utilityType), pagination);
        return ResponseEntity.ok().body(responseObject);
    }
    
    
    @GetMapping("/budget-utility")
    public ResponseEntity<ResponseObject> getProductsByBudget(
            @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination,
            @RequestParam String budgetLevel,
            @RequestParam String utilityType) {
        ResponseObject responseObject = productService.getProductsByBudgetAndUtility(BudgetLevel.valueOf(budgetLevel), UtilityType.valueOf(utilityType), pagination);
        return ResponseEntity.ok().body(responseObject);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getProductById(@PathVariable Long id) {
        ResponseObject responseObject = productService.getProductById(id);
        return ResponseEntity.ok().body(responseObject);
    }

    @GetMapping("/utility")
    public ResponseEntity<ResponseObject> getProductByUtilityType(
            @PageableDefault(page = 0, size = 20, direction = Sort.Direction.ASC) Pageable pagination,
            @RequestParam String utilityType) {
        ResponseObject responseObject = productService.getProductByUtilityType(UtilityType.valueOf(utilityType), pagination);
        return ResponseEntity.ok().body(responseObject);
    }

}
