package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "PartnerApplication")
@Data
public class PartnerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Information partnerInformation;

    @OneToMany
    private Set<PartnerService> services;

    private PartnerApplicationStatus status;

    @OneToMany
    private Set<ApplicationImage> images;

    private int successEvent;

    private String note;

    @CreatedDate
    private LocalDateTime createdDate;

}
