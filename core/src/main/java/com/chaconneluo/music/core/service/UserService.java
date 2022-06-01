package com.chaconneluo.music.core.service;

import com.chaconneluo.music.core.pojo.User;

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

    Map<String, String> getAllPublicFile(String email);

    Map<String, String> getAllFile(String email);
}
