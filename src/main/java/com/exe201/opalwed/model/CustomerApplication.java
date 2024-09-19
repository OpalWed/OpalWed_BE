package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "CustomerApplication")
@Data
public class CustomerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Information customerInformation;

    private LocalDateTime weddingDate;

    private String weddingLocation;

    private Integer numberOfGuests;

    private String weddingDescription;

    private CustomerApplicationStatus status;

    private LocalDateTime createdDate;

}
