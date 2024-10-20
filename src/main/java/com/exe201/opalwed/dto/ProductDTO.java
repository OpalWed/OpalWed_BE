package com.exe201.opalwed.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDTO {

    private Long productId;
    private Long partnerId;

    private String productName;
    private String partnerName;
    private String description;
    private String image;
    private String price;

    private String budgetLevel;
    private String weddingConcept;
    private String utility;
    private String status;

}
