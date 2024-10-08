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
    @Column(columnDefinition = "nvarchar(50)")
    private String fullName;

    private String phone;
    @Column(columnDefinition = "nvarchar(100)")
    private String address;
    @Column(columnDefinition = "nvarchar(300)")
    private String description;

    private String imageUrl;
}
