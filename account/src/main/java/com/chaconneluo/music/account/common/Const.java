package com.chaconneluo.music.account.common;

/**
 * @author ChaconneLuo
 */

public interface Const {

    String REDIS_TOKEN_PREFIX = "TOKEN:";

    String REDIS_EMAIL_PREFIX = "EMAIL:";

    String REDIS_FORBIDDEN_PREFIX = "FORBIDDEN:";

    Integer TOKEN_OVERTIME = 10 * 60;

    String APPID = "appid_fbcf976a9efe4169b295633823230788";

    String TOKEN_COOKIE_NAME = "token";

    Integer ACCOUNT_ID_OFFSET = 100000;
}
