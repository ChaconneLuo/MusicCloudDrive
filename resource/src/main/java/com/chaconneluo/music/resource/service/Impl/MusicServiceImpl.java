package com.chaconneluo.music.resource.service.Impl;

import com.chaconneluo.music.resource.dao.MusicDao;
import com.chaconneluo.music.resource.pojo.Music;
import com.chaconneluo.music.resource.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicDao musicDao;

    public Music insert(Music music) {
        return musicDao.insert(music);
    }

    public Music findById(String id) {
        return musicDao.findById(id);
    }

    @Override
    public Music deleteById(String id) {
        return null;
    }

    public List<Music> findByEmail(String email) {
        return musicDao.findByEmail(email);
    }

}
