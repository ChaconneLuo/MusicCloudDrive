package com.chaconneluo.music.account.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public String getJWT(String accountId, String seckey) {
        return JWT.create().withClaim("accountId", accountId)
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusSeconds(Const.TOKEN_OVERTIME)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .sign(Algorithm.HMAC256(seckey));
    }

    @Override
    public Cookie createCookie(String token, String accountJson) {
        redisTemplate.opsForValue().set(Const.TOKEN_PREFIX + token, accountJson, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        var cookie = new Cookie(Const.TOKEN_COOKIE_NAME, token);
        cookie.setPath("/");
        return cookie;
    }

}
