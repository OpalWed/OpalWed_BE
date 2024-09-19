package com.exe201.opalwed.controller;

import com.exe201.opalwed.service.FirebaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/files")
public class UploadController {

    private final FirebaseService firebaseService;


    @PreAuthorize("hasAnyRole('ADMIN','PARTNER')")
    @PostMapping(value = "/upload",consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file,
                                             @RequestParam("name") String name) {
        try {
            String fileUrl = firebaseService.uploadFile(file,name);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
        }
    }

}
