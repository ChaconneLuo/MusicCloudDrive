package com.chaconneluo.music.account.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chaconneluo.music.account.dao.SeckeyDao;
import com.chaconneluo.music.account.pojo.Seckey;
import com.chaconneluo.music.account.service.SeckeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ChaconneLuo
 */
@Service
@RequiredArgsConstructor
public class SeckeyServiceImpl implements SeckeyService {

    private final SeckeyDao seckeyDao;

    @Override
    public String getKey(String appid) {
        var seckey = seckeyDao.selectOne(Wrappers.<Seckey>lambdaQuery().eq(Seckey::getAppid, appid));
        if (seckey != null) {
            return seckey.getSeckey();
        } else {
            return "";
        }
    }
}
