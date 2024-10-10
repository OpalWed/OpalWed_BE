package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "CustomerApplication")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Information customerInformation;

    private LocalDateTime weddingDate;
    @Column(columnDefinition = "nvarchar(300)")
    private String weddingLocation;

    private Integer numberOfGuests;
    @Column(columnDefinition = "nvarchar(MAX)")
    private String weddingDescription;

    private CustomerApplicationStatus status;

    private LocalDateTime createdDate;

    private String requiredServicesFile;

}
