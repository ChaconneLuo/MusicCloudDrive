package com.chaconneluo.music.core.dao;

import com.chaconneluo.music.core.pojo.MusicSheet;
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
public class MusicSheetDao {

    private final MongoTemplate mongoTemplate;

    public MusicSheet insert(MusicSheet musicSheet) {
        mongoTemplate.insert(musicSheet);
        return musicSheet;
    }

    public MusicSheet findById(String id) {
        return mongoTemplate.findById(id, MusicSheet.class);
    }

    public List<MusicSheet> findByEmail(String email) {
        Criteria queryCriteria = Criteria.where("creator").is(email);
        Query query = new Query();
        if (email != null) {
            query.addCriteria(queryCriteria);
        }
        return mongoTemplate.find(query, MusicSheet.class);
    }

    public MusicSheet deleteById(String id) {
        var musicSheet = findById(id);
        mongoTemplate.remove(musicSheet);
        return musicSheet;
    }

    public List<MusicSheet> getAllSheets() {
        return mongoTemplate.findAll(MusicSheet.class);
    }

    public MusicSheet update(MusicSheet musicSheet) {
        mongoTemplate.save(musicSheet);
        return musicSheet;
    }
}
