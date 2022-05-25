package com.chaconneluo.music.resource.controller;

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

    @PostMapping("/{email}/upload")
    public void uploadFile(@PathVariable("email") String email, @RequestPart("any") MultipartFile[] mfs) {

    }
}
