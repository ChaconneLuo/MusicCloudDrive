package com.chaconneluo.music.resource.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    Boolean uploadFile(String email, String uuid, MultipartFile file);

    void checkBucket();
}
