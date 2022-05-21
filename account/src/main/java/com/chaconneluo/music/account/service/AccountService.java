package com.chaconneluo.music.account.service;

import com.chaconneluo.music.account.pojo.Account;

public interface AccountService {

    Boolean register(Account account);

    Boolean login(Account account);
}
