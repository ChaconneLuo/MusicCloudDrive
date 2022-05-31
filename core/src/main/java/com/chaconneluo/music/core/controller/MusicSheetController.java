package com.chaconneluo.music.core.controller;

import com.chaconneluo.music.core.common.MsgMapping;
import com.chaconneluo.music.core.pojo.MusicSheet;
import com.chaconneluo.music.core.service.MusicSheetService;
import com.chaconneluo.music.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChaconneLuo
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/sheet")
public class MusicSheetController {
    private final MusicSheetService musicSheetService;

    @PostMapping("/create/{email}")
    public Result<MusicSheet> createMusicSheet(@PathVariable("email") String email,
                                               @RequestParam(value = "sheet", required = false) List<String> uuids,
                                               @RequestParam("sheet-name") String sheetName) {
        var musicSheet = musicSheetService.create(email, uuids, sheetName);
        return Result.ok(musicSheet);
    }

    @PostMapping("/add/{email}/{sheet_uuid}")
    public Result<Void> AddMusicToSheet(@PathVariable("email") String email,
                                        @RequestParam(value = "sheet") List<String> uuids,
                                        @PathVariable("sheet_uuid") String sheetUUID) {
        var musicSheet = musicSheetService.addMusic(email, uuids, sheetUUID);
        if (musicSheet == null) {
            return Result.error(MsgMapping.MUSIC_SHEET_NOT_FOUND);
        } else {
            return Result.ok().build();
        }
    }

}
