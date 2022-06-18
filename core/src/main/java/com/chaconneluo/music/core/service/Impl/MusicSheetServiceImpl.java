package com.chaconneluo.music.core.service.Impl;

import com.chaconneluo.music.core.client.ResourceClient;
import com.chaconneluo.music.core.dao.MusicSheetDao;
import com.chaconneluo.music.core.pojo.MusicSheet;
import com.chaconneluo.music.core.service.MusicSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author ChaconneLuo
 */
@Service
@RequiredArgsConstructor
public class MusicSheetServiceImpl implements MusicSheetService {

    private final MusicSheetDao musicSheetDao;

    private final ResourceClient resourceClient;

    @Override
    public MusicSheet create(String email, List<String> uuids, String sheetName) {
        Map<String, String> musicsName = null;
        if (uuids != null) {
            musicsName = resourceClient.getMusicsName(uuids);
        }
        var time = LocalDateTime.now();
        var sheetId = UUID.randomUUID().toString().replace("-", "");
        var musicSheet = new MusicSheet(sheetId, sheetName, false, musicsName, email, time, time);
        musicSheetDao.insert(musicSheet);
        return musicSheet;
    }

    @Override
    public MusicSheet addMusic(String email, List<String> uuids, String sheetUUID) {
        var musicSheet = musicSheetDao.findById(sheetUUID);
        if (uuids != null) {
            var musicsNameMap = resourceClient.getMusicsName(uuids);
            var medias = musicSheet.getMedias();
            medias.putAll(musicsNameMap);
            musicSheet.setMedias(medias);
        } else {
            return null;
        }
        var time = LocalDateTime.now();
        musicSheet.setGmt_modified(time);
        musicSheetDao.insert(musicSheet);
        return musicSheet;
    }

    @Override
    public MusicSheet delMusicFromSheet(String email, List<String> uuids, String sheetUUID) {
        var musicSheet = musicSheetDao.findById(sheetUUID);
        if (uuids != null) {
            var medias = musicSheet.getMedias();
            for (String uuid : uuids) {
                medias.remove(uuid);
            }
            musicSheet.setMedias(medias);
        } else {
            return null;
        }
        var time = LocalDateTime.now();
        musicSheet.setGmt_modified(time);
        musicSheetDao.insert(musicSheet);
        return musicSheet;
    }
}
