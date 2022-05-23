package com.chaconneluo.music.account.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chaconneluo.music.account.dao.SeckeyDao;
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

    private final SeckeyDao seckeyDao;

    @Override
    public String getKey(String appid) {
        var secretKey = seckeyDao.selectOne(Wrappers.<SecretKey>lambdaQuery().eq(SecretKey::getAppid, appid));
        if (secretKey != null) {
            return secretKey.getSecretKey();
        } else {
            return "";
        }
    }
}
