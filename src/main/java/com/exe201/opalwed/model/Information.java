package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Information")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Account account;

    private String fullName;

    private String phone;

    private String address;

    private String description;

    private String imageUrl;
}
