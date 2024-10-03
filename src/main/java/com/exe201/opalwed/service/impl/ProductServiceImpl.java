package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.BudgetLevel;
import com.exe201.opalwed.model.Product;
import com.exe201.opalwed.model.WeddingConcept;
import com.exe201.opalwed.repository.ProductRepository;
import com.exe201.opalwed.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseObject getProductsByBudgetAndConcept(BudgetLevel budgetLevel, WeddingConcept weddingConcept) {

        List<Product> products = productRepository.findByBudgetLevelAndWeddingConcept(budgetLevel, weddingConcept);

        List<ProductDTO> response = new ArrayList<>();
        products.forEach(x -> response.add(mapEntityToDTO(x)));

        return ResponseObject.builder()
                .data(response)
                .isSuccess(true)
                .message("Danh sách sản phẩm")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject getProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new OpalException("Không tìm thấy sản phẩm"));

        return ResponseObject.builder()
                .data(mapEntityToDTO(product))
                .isSuccess(true)
                .message("Thông tin sản phẩm")
                .status(HttpStatus.OK)
                .build();
    }
    private ProductDTO mapEntityToDTO(Product product) {
        return ProductDTO.builder()
                .productId(product.getId())
                .partnerId(product.getPartner().getId())
                .productName(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .budgetLevel(product.getBudgetLevel().name())
                .utility(product.getUtility().name())
                .weddingConcept(product.getWeddingConcept().name())
                .status(product.getStatus().name())
                .build();
    }


}
