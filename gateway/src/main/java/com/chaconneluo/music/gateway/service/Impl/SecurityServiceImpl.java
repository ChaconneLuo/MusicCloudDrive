package com.chaconneluo.music.gateway.service.Impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chaconneluo.music.gateway.common.Const;
import com.chaconneluo.music.gateway.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final StringRedisTemplate redisTemplate;

    public static String generalJWT(String email, String seckey) {
        return JWT.create().withClaim("email", email)
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusSeconds(Const.TOKEN_OVERTIME)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .sign(Algorithm.HMAC256(seckey));
    }

    @Override
    public String getSeckey() {
        return redisTemplate.opsForValue().get(Const.APPID);
    }

    @Override
    public String updateTokenTime(String token, String seckey) {
        // redisTemplate.expire(Const.REDIS_TOKEN_PREFIX + token, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(Const.REDIS_FORBIDDEN_PREFIX + token, "", Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        var email = redisTemplate.opsForValue().get(Const.REDIS_TOKEN_PREFIX + token);
        redisTemplate.delete(Const.REDIS_TOKEN_PREFIX + token);
        var newToken = generalJWT(email, getSeckey());
        redisTemplate.opsForValue().set(Const.REDIS_TOKEN_PREFIX + newToken, email, Const.TOKEN_OVERTIME, TimeUnit.SECONDS);
        return newToken;
    }

    @Override
    public String verifyJWT(String token) {
        var verify = JWT.require(Algorithm.HMAC256(getSeckey())).build().verify(token);
        var forbidden = redisTemplate.opsForValue().get(Const.REDIS_FORBIDDEN_PREFIX + token);
        if (verify != null && forbidden == null) {
            var now = Instant.now();
            var expireTime = verify.getExpiresAt().toInstant();
            if (Duration.between(now, expireTime).getSeconds() <= Const.TOKEN_OVERTIME / 2) {
                return updateTokenTime(token, getSeckey());
            }
            return token;
        }
        return "";
    }
}
