package com.chaconneluo.music.core.service.Impl;

import com.chaconneluo.music.core.client.ResourceClient;
import com.chaconneluo.music.core.dao.UserDao;
import com.chaconneluo.music.core.pojo.User;
import com.chaconneluo.music.core.service.UserService;
import com.chaconneluo.music.core.util.ListUtil;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoClient mongoClient;

    private final ResourceClient resourceClient;

    private final UserDao userDao;

    @Override
    public User insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public User getById(String id) {
        return userDao.findById(id);
    }

    @Override
    public User deleteById(String id) {
        return userDao.deleteById(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public Map<String, Object> querySongList(String email, int page, int size) {

        var user = userDao.findByEmail(email);

        var entryList = user.getMedias().entrySet().stream().toList();

        var result = ListUtil.sub(entryList, (page - 1) * size, page * size, 1);

        Map<String, Object> map = result.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        map.put("total", entryList.size());

        return map;
    }

    @Override
    public Map<String, String> getAllPublicFile(String email) {
        var user = this.findByEmail(email);
        var userMedias = Objects.requireNonNullElse(user.getMedias(), new HashMap<String, String>());
        var publicUUIDList = new ArrayList<String>();
        userMedias.forEach((k, v) -> {
            if (v.equals(true)) {
                publicUUIDList.add(k);
            }
        });
        return resourceClient.getMusicsName(publicUUIDList);
    }

    @Override
    public Map<String, String> getAllFile(String email) {
        var user = this.findByEmail(email);
        var userMedias = Objects.requireNonNullElse(user.getMedias(), new HashMap<String, String>());
        return resourceClient.getMusicsName(userMedias.keySet().stream().toList());
    }

}
