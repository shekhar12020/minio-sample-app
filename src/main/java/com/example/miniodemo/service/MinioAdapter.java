package com.example.miniodemo.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.ObjectWriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioAdapter {

    @Autowired
    private MinioClient minioClient;

    public String uploadFile(MultipartFile file, String bucketName) throws Exception {
        String objectName = file.getOriginalFilename();

        // Upload file to the specified bucket
        ObjectWriteResponse response = minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
        return response.object();
    }

    public InputStream downloadFile(String bucketName, String objectName) throws Exception {
        // Download file from the specified bucket
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }
}