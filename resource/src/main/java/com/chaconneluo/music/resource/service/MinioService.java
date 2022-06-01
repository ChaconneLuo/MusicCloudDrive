package com.chaconneluo.music.resource.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    String uploadFile(String email, MultipartFile file);

    void checkBucket();
}
