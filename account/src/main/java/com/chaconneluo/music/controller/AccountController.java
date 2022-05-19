package com.chaconneluo.music.controller;

import com.chaconneluo.music.common.MsgMapping;
import com.chaconneluo.music.pojo.Account;
import com.chaconneluo.music.service.AccountService;
import com.chaconneluo.music.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ChaconneLuo
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public Result<Map<String, String>> register(@RequestBody Account account) {
        var date = LocalDateTime.now();
        account.setGmtCreate(date);
        account.setGmtModified(date);
        var registerStatus = accountService.register(account);
        if (registerStatus) {
            return Result.ok(Map.of("status", MsgMapping.REGISTER_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.EMAIL_REPEAT));
        }
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Account account){
        var loginSuccess = accountService.login(account);
        if (loginSuccess) {
            return Result.ok(Map.of("status", MsgMapping.LOGIN_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.USERNAME_OR_PASSWORD_ERROR));
        }
    }

}
