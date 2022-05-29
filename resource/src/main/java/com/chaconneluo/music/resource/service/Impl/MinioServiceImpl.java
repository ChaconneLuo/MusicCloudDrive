package com.chaconneluo.music.resource.service.Impl;

import com.chaconneluo.music.resource.pojo.Music;
import com.chaconneluo.music.resource.pojo.User;
import com.chaconneluo.music.resource.service.MinioService;
import com.chaconneluo.music.resource.service.MusicService;
import com.chaconneluo.music.resource.service.UserService;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private final MusicService musicService;

    private final UserService userService;
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
        try {
            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(uuid)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        var time = LocalDateTime.now();
        musicService.insert(new Music(uuid,
                fileName,
                email,
                file.getSize(),
                time,
                time));
        var user = userService.findByEmail(email);
        if (user == null) {
            userService.insert(new User(email,
                    5000000L,
                    file.getSize(),
                    Map.of(uuid, false),
                    time,
                    time));
        } else {
            var newMedias = user.getMedias();
            var usedCapacity = user.getUsedCapacity() + file.getSize();
            newMedias.put(uuid, false);
            user.setMedias(newMedias);
            user.setUsedCapacity(usedCapacity);
            userService.update(user);
        }
        return uuid;
    }

}
