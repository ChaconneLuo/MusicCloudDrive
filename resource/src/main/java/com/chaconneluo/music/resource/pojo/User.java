package com.chaconneluo.music.resource.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@Data
@Document(collection =  "user")
public class User {

    @Id
    private String id;

    private String email;

    private Long capacity;

    private Long usedCapacity;

    private Map<String, Boolean> medias;


    private List<String> musicSheet;


    private List<String> collectMusicSheet;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;

    public User(String email, Long capacity, Long usedCapacity, Map<String, Boolean> medias, LocalDateTime gmt_create, LocalDateTime gmt_modified) {
        this.email = email;
        this.capacity = capacity;
        this.usedCapacity = usedCapacity;
        this.medias = medias;
        this.gmt_create = gmt_create;
        this.gmt_modified = gmt_modified;
    }
}
