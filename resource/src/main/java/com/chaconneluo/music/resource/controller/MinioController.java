package com.chaconneluo.music.resource.controller;

import com.chaconneluo.music.resource.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ChaconneLuo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class MinioController {

    private final MinioService minioService;

    @PostMapping("/upload/{email}")
    public void uploadFile(@PathVariable("email") String email, @RequestPart("any") MultipartFile[] mfs) {
        minioService.checkBucket();
        for (var file : mfs) {
            minioService.uploadFile(email, file);
        }
    }

}
