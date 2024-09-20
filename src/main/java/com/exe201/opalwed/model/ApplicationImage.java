package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ApplicationImage")
@Data
public class ApplicationImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PartnerApplicationId")
    private PartnerApplication application;

    private String url;
}
