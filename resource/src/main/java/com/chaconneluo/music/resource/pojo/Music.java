package com.chaconneluo.music.resource.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * @author ChaconneLuo
 */

@Data
@Document(collection = "music")
@AllArgsConstructor
public class Music {

    @Id
    private String id;

    private String musicName;

    private String uploadEmail;

    private Long size;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;
}
