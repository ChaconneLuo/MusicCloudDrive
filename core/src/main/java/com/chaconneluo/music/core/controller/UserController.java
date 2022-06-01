package com.chaconneluo.music.core.controller;

import com.chaconneluo.music.core.pojo.User;
import com.chaconneluo.music.core.service.UserService;
import com.chaconneluo.music.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/public/{email}")
    public Result<Map<String, String>> getAllPublicFile(@PathVariable("email") String email) {
        var musicInfo = userService.getAllPublicFile(email);
        return Result.ok().data(musicInfo);

    }

    @PostMapping("/all/{email}")
    public Result<Map<String, String>> getAllFile(@PathVariable("email") String email) {
        var musicInfo = userService.getAllFile(email);
        return Result.ok().data(musicInfo);
    }

    @PostMapping("/add/{email}")
    public Result<Void> addMusic(@PathVariable("email") String email, @RequestParam("musicUUID") String musicUUID, @RequestParam("fileSize") Long fileSize) {
        var user = userService.findByEmail(email);
        var time = LocalDateTime.now();
        if (user == null) {
            userService.insert(new User(email, 5000000L, fileSize, Map.of(musicUUID, false), time, time));
        } else {
            var newMedias = user.getMedias();
            if (user.getUsedCapacity() + fileSize <= user.getCapacity()) {
                var usedCapacity = user.getUsedCapacity() + fileSize;
                newMedias.put(musicUUID, false);
                user.setMedias(newMedias);
                user.setUsedCapacity(usedCapacity);
                userService.update(user);
            } else {
                return Result.error().build();
            }
        }
        return Result.ok().build();
    }
}
