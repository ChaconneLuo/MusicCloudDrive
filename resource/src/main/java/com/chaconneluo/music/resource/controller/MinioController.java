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

    @PostMapping("/{email}/upload/{uuid}")
    public void uploadFile(@PathVariable("email") String email, @PathVariable("uuid") String uuid, @RequestPart("any") MultipartFile[] mfs) {
        minioService.checkBucket();
        for (var file : mfs) {
            minioService.uploadFile(email, uuid, file);
        }
    }

}
