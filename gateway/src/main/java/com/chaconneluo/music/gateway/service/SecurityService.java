package com.chaconneluo.music.gateway.service;

public interface SecurityService {
    String getSeckey();

    String updateTokenTime(String token, String seckey);

    String verifyJWT(String token);
}
