package com.AnimeApp.web.controller;


import com.AnimeApp.service.Minio.MinioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping("/file-url")
    public String getPresignedUrl(@RequestParam String fileName) {
        try {
            return minioService.getPresignedUrl(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating URL";
        }
    }
}
