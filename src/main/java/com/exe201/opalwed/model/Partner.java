package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Partner")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "nvarchar(50)")
    private String partnerName;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Information partnerInformation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "partner",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartnerUtility> utilities;

    private PartnerStatus status;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PartnerImage> images;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Product> products;

    private int successEvent;
    @Column(columnDefinition = "nvarchar(MAX)")
    private String note;

    public void setImages(Set<PartnerImage> images) {
        images.forEach(i -> i.setPartner(this));
        this.images = images;
    }

    public void setUtilities(Set<PartnerUtility> utilities) {
        utilities.forEach(x -> x.setPartner(this));
        this.utilities = utilities;
    }

    public void setProducts(Set<Product> products) {
        products.forEach(p -> p.setPartner(this));
        this.products = products;
    }
}
