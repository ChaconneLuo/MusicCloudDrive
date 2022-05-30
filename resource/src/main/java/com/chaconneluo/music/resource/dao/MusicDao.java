package com.chaconneluo.music.resource.dao;

import com.chaconneluo.music.resource.pojo.Music;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ChaconneLuo
 */

@Repository
@RequiredArgsConstructor
public class MusicDao {
    private final MongoTemplate mongoTemplate;

    public Music insert(Music music) {
        mongoTemplate.insert(music);
        return music;
    }

    public Music findById(String id) {
        return mongoTemplate.findById(id, Music.class);
    }

    public List<Music> findByEmail(String email) {
        Criteria queryCriteria = Criteria.where("email").is(email);
        Query query = new Query();
        if (email != null) {
            query.addCriteria(queryCriteria);
        }
        return mongoTemplate.find(query, Music.class);
    }

    public Music deleteById(String id) {
        var music = findById(id);
        mongoTemplate.remove(music);
        return music;
    }
}
