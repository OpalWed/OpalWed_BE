package com.exe201.opalwed.security;


import com.exe201.opalwed.dto.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody LoginRequest request) {

        Map<String, Object> data = authService.createLoginInfo(request);

        var responseObject = ResponseObject.builder()
                .data(data)
                .isSuccess(true)
                .message("Login successful")
                .status(HttpStatus.OK)
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody RegisterRequest request) {
        ResponseObject data =  authService.register(request);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/requestOTP")
    public ResponseEntity<ResponseObject> getOtpByMail(@RequestParam String email) {
        return ResponseEntity.ok().body(authService.sendOtp(email));
    }

    @GetMapping("/forget-password")
    public ResponseEntity<ResponseObject> forgetPassword(@RequestParam String email) {
        return ResponseEntity.ok().body(authService.forgetPassword(email));
    }

    @PostMapping("/update-otp-password")
    public ResponseEntity<ResponseObject> updateOPTPassword(@Valid @RequestBody ChangePasswordOTP request) {
        return ResponseEntity.ok().body(authService.changePasswordWithOTP(request));
    }

    @PostMapping("/validateOTP")
    public ResponseEntity<ResponseObject> getOtpByMail(@RequestBody ValidateOTPRequest request) {
        return ResponseEntity.ok().body(authService.validateOTP(request));
    }

    @PutMapping("/update-password")
    public ResponseEntity<ResponseObject> updatePassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {
        ResponseObject responseObject = authService.changePassword(authentication,request);
        return ResponseEntity.ok().body(responseObject);
    }



}
