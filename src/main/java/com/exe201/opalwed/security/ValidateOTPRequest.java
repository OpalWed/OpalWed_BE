package com.exe201.opalwed.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidateOTPRequest {
    String email;
    String otp;
}
