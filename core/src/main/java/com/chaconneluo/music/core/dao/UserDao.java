package com.chaconneluo.music.core.dao;

import com.chaconneluo.music.core.pojo.User;
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
public class UserDao {

    private final MongoTemplate mongoTemplate;

    public User insert(User user) {
        mongoTemplate.insert(user);
        return user;
    }

    public User findById(String id) {
        return mongoTemplate.findById(id, User.class);
    }

    public User findByEmail(String email) {
        Criteria queryCriteria = Criteria.where("email").is(email);
        Query query = new Query();
        if (email != null) {
            query.addCriteria(queryCriteria);
        }
        return mongoTemplate.findOne(query, User.class);
    }

    public User deleteById(String id) {
        var user = findById(id);
        mongoTemplate.remove(user);
        return user;
    }

    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    public User update(User user) {
        mongoTemplate.save(user);
        return user;
    }


}
