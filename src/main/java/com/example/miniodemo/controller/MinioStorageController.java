package com.example.miniodemo.controller;


import com.example.miniodemo.service.MinioAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/files")
public class MinioStorageController {

    @Autowired
    private MinioAdapter minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("bucketName") String bucketName) {
        try {
            String objectName = minioService.uploadFile(file, bucketName);
            return ResponseEntity.ok("File uploaded successfully: " + objectName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("bucketName") String bucketName,
                                               @RequestParam("objectName") String objectName) {
        try (InputStream inputStream = minioService.downloadFile(bucketName, objectName)) {
            byte[] fileBytes = inputStream.readAllBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + objectName + "\"");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}