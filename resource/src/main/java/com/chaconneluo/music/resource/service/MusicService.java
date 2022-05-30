package com.chaconneluo.music.resource.service;

import com.chaconneluo.music.resource.pojo.Music;

import java.util.List;

public interface MusicService {

    Music insert(Music music);

    Music findById(String id);

    Music deleteById(String id);

    List<Music> findByEmail(String email);
}
