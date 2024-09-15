package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Information")
@Data
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Account account;

    private String fullName;

    private String phone;

    private String address;

    private String description;

    private String imageUrl;
}
