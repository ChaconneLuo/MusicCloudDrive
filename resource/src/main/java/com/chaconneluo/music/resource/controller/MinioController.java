package com.chaconneluo.music.resource.controller;

import com.chaconneluo.music.resource.service.MinioService;
import com.chaconneluo.music.resource.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

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

    @PostMapping("/get/public/{email}")
    public Result<Map<String, String>> getAllPublicFile(@PathVariable("email") String email) {
        var musicInfo = minioService.getAllPublicFile(email);
        return Result.ok().data(musicInfo);

    }

    @PostMapping("/get/all/{email}")
    public Result<Map<String, String>> getAllFile(@PathVariable("email") String email){
        var musicInfo = minioService.getAllFile(email);
        return Result.ok().data(musicInfo);
    }

}
