package com.chaconneluo.music.account.service;

import com.chaconneluo.music.account.pojo.Account;

public interface AccountService {

    Boolean insert(Account account);

    Boolean check(Account account);

    Boolean updatePassword(String email,String oldPassword,String newPassword);

    Account updateUsername(String email,String newUsername);
}
