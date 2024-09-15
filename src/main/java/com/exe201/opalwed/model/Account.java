package com.exe201.opalwed.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Account")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private Role accountRole;

}
