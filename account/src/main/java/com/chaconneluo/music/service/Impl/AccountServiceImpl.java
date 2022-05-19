package com.chaconneluo.music.service.Impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chaconneluo.music.dao.AccountDao;
import com.chaconneluo.music.pojo.Account;
import com.chaconneluo.music.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ChaconneLuo
 */

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao;

    @Override
    public Boolean register(Account account) {
        return accountDao.insert(account)>0;
    }

    @Override
    public Boolean login(Account account) {
        return accountDao.selectCount(Wrappers.<Account>lambdaQuery()
                .eq(Account::getEmail, account.getEmail())
                .eq(Account::getPassword,account.getPassword())) == 1L;

    }
}
