package com.chaconneluo.music.account.service.Impl;

import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
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
    public Cookie create(String email, String seckey, String accountJson) {
        //var token = JWTUtil.generalJWT(email, seckey);
        var token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(Const.REDIS_EMAIL_PREFIX + email, accountJson, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(Const.REDIS_TOKEN_PREFIX + token, email, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        var cookie = new Cookie(Const.TOKEN_COOKIE_NAME, token);
        cookie.setPath("/");
        return cookie;
    }

    @Override
    public void deleteToken(String token) {
        redisTemplate.delete(Const.REDIS_TOKEN_PREFIX + token);
        redisTemplate.opsForValue().set(Const.REDIS_FORBIDDEN_PREFIX + token, "", Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
    }

    @Override
    public void writeSeckey(String appid, String seckey) {
        redisTemplate.opsForValue().set(appid, seckey);
    }


}
