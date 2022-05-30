package com.chaconneluo.music.resource.controller;

import com.chaconneluo.music.resource.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/info")
public class SheetInfoController {

    private final MusicService musicService;

    @PostMapping("/getMusicsName")
    public Map<String, String> getMusicsName(List<String> uuids) {
        var musicsMap = new HashMap<String, String>();
        for (var uuid : uuids) {
            var music = musicService.findById(uuid);
            musicsMap.put(uuid, music.getMusicName());
        }
        return musicsMap;
    }
}
