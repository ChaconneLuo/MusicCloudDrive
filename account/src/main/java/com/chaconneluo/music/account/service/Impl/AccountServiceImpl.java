package com.chaconneluo.music.account.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.dao.AccountDao;
import com.chaconneluo.music.account.pojo.Account;
import com.chaconneluo.music.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Override
    public Boolean register(Account account) {
        var date = LocalDateTime.now();
        account.setGmtCreate(date);
        account.setGmtModified(date);
        account.setAccountId(accountDao.getMaxId().add(BigInteger.valueOf(Const.ACCOUNT_ID_OFFSET + 1)));
        return accountDao.insert(account) > 0;
    }

    @Override
    public Boolean login(Account account) {
        return accountDao.selectCount(Wrappers.<Account>lambdaQuery()
                .eq(Account::getEmail, account.getEmail())
                .eq(Account::getPassword,account.getPassword())) == 1L;

    }
}
