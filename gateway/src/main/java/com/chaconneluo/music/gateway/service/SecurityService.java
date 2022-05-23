package com.chaconneluo.music.gateway.service;

public interface SecurityService {
    String getSecretKey();

    String updateToken(String token, String secretkey);

    String verifyJWT(String token);

    String decodeEmailFromJWT(String token);
}
