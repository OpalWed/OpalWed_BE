package com.exe201.opalwed.security;


import com.exe201.opalwed.dto.ResponseObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> data =  authService.register(request);
        var responseObject = ResponseObject.builder()
                .data(data)
                .isSuccess(true)
                .message("Register successful")
                .build();
        return ResponseEntity.ok().body(responseObject);
    }

}
