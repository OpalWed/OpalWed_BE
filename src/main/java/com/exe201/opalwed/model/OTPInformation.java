package com.exe201.opalwed.model;

import com.exe201.opalwed.utils.OpalUtils;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "OTPInformation")
@Getter
public class OTPInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String otpCode;
    private LocalDateTime expire;

    public OTPInformation() {
        otpCode = OpalUtils.generateRandomOTP();
        expire = LocalDateTime.now().plusMinutes(10);
    }

    public void resetOTP() {
        otpCode = OpalUtils.generateRandomOTP();
        expire = LocalDateTime.now().plusMinutes(10);
    }

}
