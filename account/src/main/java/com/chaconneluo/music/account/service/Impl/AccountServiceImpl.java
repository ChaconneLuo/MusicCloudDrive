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
    public Boolean insert(Account account) {
        var date = LocalDateTime.now();
        account.setGmtCreate(date);
        account.setGmtModified(date);
        account.setAccountId(accountDao.getMaxId().add(BigInteger.valueOf(Const.ACCOUNT_ID_OFFSET + 1)));
        return accountDao.insert(account) > 0;
    }

    @Override
    public Boolean check(Account account) {
        return accountDao.selectCount(Wrappers.<Account>lambdaQuery()
                .eq(Account::getEmail, account.getEmail())
                .eq(Account::getPassword,account.getPassword())) == 1L;

    }

    @Override
    public Boolean updatePassword(String email, String oldPassword, String newPassword) {
        var requiredAccount = accountDao.selectOne(Wrappers.<Account>lambdaQuery()
                .eq(Account::getEmail,email)
                .eq(Account::getPassword, oldPassword));
        if(requiredAccount!=null)
        {
            requiredAccount.setPassword(newPassword);
            requiredAccount.setGmtModified(LocalDateTime.now());
            accountDao.updateById(requiredAccount);

            return true;
        }
        return false;
    }

    @Override
    public Account updateUsername(String email, String newUsername) {
        var requiredAccount = accountDao.selectOne(Wrappers.<Account>lambdaQuery()
                .eq(Account::getEmail,email));
        if(requiredAccount!=null){
            requiredAccount.setUsername(newUsername);
            requiredAccount.setGmtModified(LocalDateTime.now());
            accountDao.updateById(requiredAccount);
            return requiredAccount;
        }else {
            return null;
        }

    }
}
