package com.chaconneluo.music.account.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.chaconneluo.music.account.common.Const;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author ChaconneLuo
 */

public class JWTUtil {
    private JWTUtil() {

    }

    public static String generalJWT(String email, String seckey) {
        return JWT.create().withClaim("email", email)
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusSeconds(Const.TOKEN_OVERTIME)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()))
                .sign(Algorithm.HMAC256(seckey));
    }

}
