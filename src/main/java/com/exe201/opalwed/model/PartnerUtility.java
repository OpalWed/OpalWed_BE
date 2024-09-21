package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "PartnerUtility")
@Setter
@Getter
@NoArgsConstructor
public class PartnerUtility {

    public PartnerUtility(UtilityType utilityType) {
        this.utilityType = utilityType;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner")
    private Partner partner;

    private UtilityType utilityType;

}
