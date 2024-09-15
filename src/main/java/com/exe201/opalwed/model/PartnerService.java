package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "PartnerService")
@Data
public class PartnerService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartnerApplication partnerApplication;

    private ServiceType serviceType;

}
