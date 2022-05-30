package com.chaconneluo.music.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@Data
@Document(collection = "music_sheet")
@AllArgsConstructor
public class MusicSheet {

    @Id
    private String id;

    private String musicSheetName;

    private Boolean musicSheetPublic;

    private Map<String, String> medias;

    private String creator;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;

}
