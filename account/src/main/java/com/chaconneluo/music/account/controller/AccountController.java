package com.chaconneluo.music.account.controller;

import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.common.MsgMapping;
import com.chaconneluo.music.account.pojo.Account;
import com.chaconneluo.music.account.service.AccountService;
import com.chaconneluo.music.account.service.SeckeyService;
import com.chaconneluo.music.account.service.TokenService;
import com.chaconneluo.music.account.util.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ChaconneLuo
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    private final SeckeyService seckeyService;

    @PostMapping("/register")
    public Result<Map<String, String>> register(@RequestBody Account account, HttpServletResponse resp) throws JsonProcessingException {

        var registerStatus = accountService.register(account);
        if (registerStatus) {
            login(account, resp);
            return Result.ok(Map.of("status", MsgMapping.REGISTER_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.EMAIL_REPEAT));
        }
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Account account, HttpServletResponse resp) throws JsonProcessingException {
        var loginSuccess = accountService.login(account);
        if (loginSuccess) {
            var seckey = seckeyService.getKey(Const.APPID);
            if (seckey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            resp.addCookie(tokenService.create(String.valueOf(account.getAccountId())
                    , seckey
                    , objectMapper.writeValueAsString(account)));
            return Result.ok(Map.of("status", MsgMapping.LOGIN_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.USERNAME_OR_PASSWORD_ERROR));
        }
    }


}
