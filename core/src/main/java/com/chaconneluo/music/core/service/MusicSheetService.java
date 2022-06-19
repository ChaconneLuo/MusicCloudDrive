package com.chaconneluo.music.core.service;

import com.chaconneluo.music.core.pojo.MusicSheet;

import java.util.List;

public interface MusicSheetService {
    MusicSheet create(String email, List<String> uuids, String sheetName);

    MusicSheet addMusic(String email, List<String> uuids, String sheetUUID);

    MusicSheet delMusicFromSheet(String email, List<String> uuids, String sheetUUID);

    Boolean shareSheet(String email, String sheetUUID);
}
