package com.chaconneluo.music.resource.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@Data
@Document(collation = "user")
public class User {

    @Id
    private String id;

    private Integer capacity;

    private Integer usedCapacity;

    private Map<String, Boolean> medias;

    private LocalDateTime gmt_create;

    private LocalDateTime gmt_modified;

}
