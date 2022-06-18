package com.chaconneluo.music.account.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chaconneluo.music.account.dao.SecretKeyDao;
import com.chaconneluo.music.account.pojo.SecretKey;
import com.chaconneluo.music.account.service.SecretKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ChaconneLuo
 */
@Service
@RequiredArgsConstructor
public class SecretKeyServiceImpl implements SecretKeyService {

    private final SecretKeyDao secretKeyDao;

    @Override
    public String getKey(String appid) {
        var secretKey = secretKeyDao.selectOne(Wrappers.<SecretKey>lambdaQuery().eq(SecretKey::getAppid, appid));
        if (secretKey != null) {
            return secretKey.getSecretKey();
        } else {
            return "";
        }
    }
}
