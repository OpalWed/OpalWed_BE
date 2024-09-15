package com.exe201.opalwed.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Image")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long referId;


}
