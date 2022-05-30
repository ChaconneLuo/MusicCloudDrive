package com.chaconneluo.music.resource.service;

import com.chaconneluo.music.resource.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User insert(User user);

    User getById(String id);

    User deleteById(String id);

    User update(User user);

    User findByEmail(String email);

    List<User> getAllUsers();

    Map<String, Object> querySongList(String email, int page, int size);
}
