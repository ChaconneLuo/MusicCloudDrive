package com.chaconneluo.music.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@FeignClient(value = "resource-service", path = "/info", contextId = "ResourceClient")
public interface ResourceClient {

    @PostMapping("/getMusicsName")
    Map<String, String> getMusicsName(List<String> uuids);
}
