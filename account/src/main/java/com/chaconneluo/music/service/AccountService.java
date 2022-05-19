package com.chaconneluo.music.service;

import com.chaconneluo.music.pojo.Account;

public interface AccountService {

    Boolean register(Account account);

    Boolean login(Account account);
}
