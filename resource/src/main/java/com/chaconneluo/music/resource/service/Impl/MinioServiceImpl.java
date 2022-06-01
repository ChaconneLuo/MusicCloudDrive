package com.chaconneluo.music.resource.service.Impl;

import com.chaconneluo.music.resource.client.CoreClient;
import com.chaconneluo.music.resource.pojo.Music;
import com.chaconneluo.music.resource.service.MinioService;
import com.chaconneluo.music.resource.service.MusicService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final MusicService musicService;

    private final CoreClient coreClient;
    @Value("${minio.bucketName}")
    private String bucketName;

    public void checkBucket() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBucket() {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadFile(String email, MultipartFile file) {
        var fileName = file.getOriginalFilename();
        var uuid = UUID.randomUUID().toString().replace("-", "");
        assert fileName != null;
        var time = LocalDateTime.now();
        var insertResult = coreClient.addMusic(email, uuid, file.getSize());
        if (!insertResult.failed()) {
            musicService.insert(new Music(uuid,
                    fileName,
                    email,
                    file.getSize(),
                    time,
                    time));
            try {
                minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(uuid).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return uuid;
        }
        return "";
    }
}
