package com.chaconneluo.music.account.service;

import javax.servlet.http.Cookie;

public interface TokenService {

    Cookie create(String email, String secretKey, String accountJson);

    void deleteAllToken(String token, String secretKey);

    void deleteSingleToken(String key);

    void writeSecretKey(String appid, String secretKey);
}
