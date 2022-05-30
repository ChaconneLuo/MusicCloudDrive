package com.chaconneluo.music.resource.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MinioService {
    String uploadFile(String email, MultipartFile file);

    Map<String, String> getAllPublicFile(String email);

    Map<String, String> getAllFile(String email);

    void checkBucket();
}
