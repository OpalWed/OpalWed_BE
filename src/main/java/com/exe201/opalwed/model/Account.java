package com.exe201.opalwed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private AccountStatus status;

    private Role accountRole;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private OTPInformation otp;

}
