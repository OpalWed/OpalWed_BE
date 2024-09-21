package com.exe201.opalwed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PartnerImage")
@Data
public class PartnerImage {

    public PartnerImage(String url) {
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "partner")
    private Partner partner;

    private String url;
}
