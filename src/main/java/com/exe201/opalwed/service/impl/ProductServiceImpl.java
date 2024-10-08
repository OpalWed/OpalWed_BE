package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.dto.PaginationResponse;
import com.exe201.opalwed.dto.ProductDTO;
import com.exe201.opalwed.dto.ResponseObject;
import com.exe201.opalwed.exception.OpalException;
import com.exe201.opalwed.model.*;
import com.exe201.opalwed.repository.PartnerRepository;
import com.exe201.opalwed.repository.ProductRepository;
import com.exe201.opalwed.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public ResponseObject getProductsByBudgetAndConceptAndUtility(BudgetLevel budgetLevel, WeddingConcept weddingConcept, UtilityType utilityType , Pageable pageable) {

        Page<ProductDTO> products = productRepository.findByBudgetLevelAndWeddingConceptAndStatusAndUtility(
                budgetLevel, weddingConcept ,ProductStatus.AVAILABLE , utilityType , pageable)
                .map(this::mapEntityToDTO);

        return ResponseObject.builder()
                .data(new PaginationResponse<>(products))
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

    @Override
    public ResponseObject getProductByUtilityType(UtilityType utilityType, Pageable pageable) {

        Page<ProductDTO> products = productRepository.findByUtilityAndStatus(utilityType,ProductStatus.AVAILABLE , pageable)
                .map(this::mapEntityToDTO);

        return ResponseObject.builder()
                .data(new PaginationResponse<>(products))
                .isSuccess(true)
                .message("Danh sách sản phẩm")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject addProduct(ProductDTO product) {

        Partner partner = partnerRepository.findById(product.getPartnerId()).orElseThrow(() -> new OpalException("Không tìm thấy đối tác"));

        Product newProduct = Product.builder()
                .name(product.getProductName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .budgetLevel(BudgetLevel.valueOf(product.getBudgetLevel()))
                .weddingConcept(WeddingConcept.valueOf(product.getWeddingConcept()))
                .utility(UtilityType.valueOf(product.getUtility()))
                .status(ProductStatus.valueOf(product.getStatus()))
                .partner(partner)
                .build();

        newProduct = productRepository.save(newProduct);

        return ResponseObject.builder()
                .data(mapEntityToDTO(newProduct))
                .isSuccess(true)
                .message("Thêm sản phẩm thành công!")
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    public ResponseObject updateProduct(ProductDTO product) {

            Product updateProduct = productRepository.findById(product.getProductId()).orElseThrow(() -> new OpalException("Không tìm thấy sản phẩm"));

            updateProduct.setName(product.getProductName());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setImage(product.getImage());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setBudgetLevel(BudgetLevel.valueOf(product.getBudgetLevel()));
            updateProduct.setWeddingConcept(WeddingConcept.valueOf(product.getWeddingConcept()));
            updateProduct.setUtility(UtilityType.valueOf(product.getUtility()));
            updateProduct.setStatus(ProductStatus.valueOf(product.getStatus()));

            updateProduct = productRepository.save(updateProduct);

            return ResponseObject.builder()
                    .data(mapEntityToDTO(updateProduct))
                    .isSuccess(true)
                    .message("Cập nhật sản phẩm thành công!")
                    .status(HttpStatus.OK)
                    .build();
    }

    @Override
    public ResponseObject deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new OpalException("Không tìm thấy sản phẩm"));
        product.setStatus(ProductStatus.DELETED);
        productRepository.save(product);

        return ResponseObject.builder()
                .data(null)
                .isSuccess(true)
                .message("Xóa sản phẩm thành công!")
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
