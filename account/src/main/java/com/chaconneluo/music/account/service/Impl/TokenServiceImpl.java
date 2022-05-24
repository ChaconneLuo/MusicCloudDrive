package com.chaconneluo.music.account.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.service.TokenService;
import com.chaconneluo.music.account.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public Cookie create(String email, String secretKey, String accountJson) {
        var token = JWTUtil.generalJWT(email, secretKey);
        // var token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(Const.REDIS_EMAIL_PREFIX + email, accountJson, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(Const.REDIS_TOKEN_PREFIX + email + ":" + token, email, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        var cookie = new Cookie(Const.TOKEN_COOKIE_NAME, token);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    public void deleteAllToken(String token, String secretKey) {
        var email = getEmailFromToken(token, secretKey);
        var keys = redisTemplate.keys(Const.REDIS_TOKEN_PREFIX + email + ":*");
        Objects.requireNonNull(keys).forEach(this::deleteSingleToken);
    }

    @Override
    public void deleteSingleToken(String key) {
        var email = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        redisTemplate.opsForValue().set(Const.REDIS_FORBIDDEN_PREFIX + key.substring((Const.REDIS_TOKEN_PREFIX + email + ":").length()), "", Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
    }

    private String getEmailFromToken(String token, String secretKey) {
        return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token).getClaim("email").asString();
    }

    @Override
    public void writeSecretKey(String appid, String secretKey) {
        redisTemplate.opsForValue().set(appid, secretKey);
    }


}
