package com.chaconneluo.music.account.service;

import javax.servlet.http.Cookie;

public interface TokenService {
    String getJWT(String accountId, String seckey);

    Cookie create(String accountId, String seckey, String accountJson);
}
