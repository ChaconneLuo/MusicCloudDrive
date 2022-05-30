package com.chaconneluo.music.core.controller;

import com.chaconneluo.music.core.pojo.MusicSheet;
import com.chaconneluo.music.core.service.MusicSheetService;
import com.chaconneluo.music.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author ChaconneLuo
 */

@Controller
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
        return null;
    }

}
