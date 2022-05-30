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
import java.util.ArrayList;
import java.util.HashMap;
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
            if (user.getUsedCapacity() + file.getSize() <= user.getCapacity()) {
                var usedCapacity = user.getUsedCapacity() + file.getSize();
                newMedias.put(uuid, false);
                user.setMedias(newMedias);
                user.setUsedCapacity(usedCapacity);
                userService.update(user);
            } else {
                musicService.deleteById(uuid);
                return "";
            }
        }
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(uuid).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return uuid;
    }

    @Override
    public Map<String, String> getAllPublicFile(String email) {
        var user = userService.findByEmail(email);
        var userMedias = user.getMedias();
        var publicUUIDList = new ArrayList<String>();
        userMedias.forEach((k, v) -> {
            if (v.equals(true)) {
                publicUUIDList.add(k);
            }
        });
        var userPublicMedia = new HashMap<String, String>();
        for (var uuid : publicUUIDList) {
            var music = musicService.findById(uuid);
            userPublicMedia.put(uuid, music.getMusicName());
        }
        return userPublicMedia;
    }

    @Override
    public Map<String, String> getAllFile(String email) {
        var user = userService.findByEmail(email);
        var userMedias = user.getMedias();
        var userAllMedia = new HashMap<String, String>();
        for (var entry : userMedias.entrySet()) {
            var uuid = entry.getKey();
            var music = musicService.findById(uuid);
            userAllMedia.put(uuid, music.getMusicName());
        }
        return userAllMedia;
    }


}
