package com.chaconneluo.music.resource.client;

import com.chaconneluo.music.resource.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ChaconneLuo
 */
@FeignClient(value = "core-service", path = "/user", contextId = "CoreClient")
public interface CoreClient {

    @PostMapping("/add/{email}")
    Result<Void> addMusic(@PathVariable("email") String email, @RequestParam("musicUUID") String musicUUID, @RequestParam("fileSize") Long fileSize);
}
