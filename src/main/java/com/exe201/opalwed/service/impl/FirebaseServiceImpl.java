package com.exe201.opalwed.service.impl;

import com.exe201.opalwed.service.FirebaseService;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final String bucketName = "opalwed-16.appspot.com";

    @Override
    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        fileName = fileName + "-" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();

        // Tải file lên
        Blob blob = bucket.create(fileName, file.getInputStream(), "image/*");
        blob.updateAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}
