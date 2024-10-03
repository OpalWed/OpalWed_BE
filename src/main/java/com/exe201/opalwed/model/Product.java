package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(300)")
    private String name;
    @Column(columnDefinition = "nvarchar(MAX)")
    private String description;
    private String image;
    private String price;

    private BudgetLevel budgetLevel;
    private WeddingConcept weddingConcept;
    private UtilityType utility;
    private ProductStatus status;

    @ManyToOne
    @JoinColumn(name = "partner")
    private Partner partner;


}
