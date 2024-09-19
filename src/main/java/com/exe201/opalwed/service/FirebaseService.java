package com.exe201.opalwed.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FirebaseService {
    String uploadFile(MultipartFile file,String fileName) throws IOException;

}
