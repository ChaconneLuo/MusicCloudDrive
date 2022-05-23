package com.chaconneluo.music.account.service;

import javax.servlet.http.Cookie;

public interface TokenService {

    Cookie create(String email, String seckey, String accountJson);

    void deleteToken(String token);

    void writeSeckey(String appid, String seckey);
}
