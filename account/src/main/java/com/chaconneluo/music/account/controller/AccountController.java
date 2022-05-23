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
import org.springframework.web.bind.annotation.*;

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

        var registerStatus = accountService.insert(account);
        if (registerStatus) {
            login(account, resp);
            return Result.ok(Map.of("status", MsgMapping.REGISTER_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.EMAIL_REPEAT));
        }
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Account account, HttpServletResponse resp) throws JsonProcessingException {
        var loginSuccess = accountService.check(account);
        if (loginSuccess) {
            var seckey = seckeyService.getKey(Const.APPID);
            if (seckey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            resp.addCookie(tokenService.create(String.valueOf(account.getEmail())
                    , seckey
                    , objectMapper.writeValueAsString(account)));
            tokenService.writeSeckey(Const.APPID, seckey);
            return Result.ok(Map.of("status", MsgMapping.LOGIN_SUCCESS));
        } else {
            return Result.ok(Map.of("status", MsgMapping.LOGIN_ERROR));
        }
    }

    @PostMapping("/editPassword")
    public Result<Map<String, String>> editPassword(@RequestParam("email") String email,
                                                    @RequestParam("oldPassword") String oldPassword,
                                                    @RequestParam("newPassword") String newPassword,
                                                    @RequestHeader("token") String token) {
        var updateSuccess = accountService.updatePassword(email, oldPassword, newPassword);
        if (updateSuccess) {
            tokenService.deleteToken(token);
            return Result.ok(Map.of("status", MsgMapping.PASSWORD_EDIT_SUCCESS));
        }
        return Result.ok(Map.of("status", MsgMapping.PASSWORD_EDIT_ERROR));
    }

    @PostMapping("/editUsername")
    public Result<Map<String, String>> editUsername(@RequestParam("email") String email,
                                                    @RequestParam("username") String newUsername,
                                                    @RequestHeader("token") String token,
                                                    HttpServletResponse resp) throws JsonProcessingException {
        var requiredAccount = accountService.updateUsername(email, newUsername);
        if (requiredAccount != null) {
            var seckey = seckeyService.getKey(Const.APPID);
            if (seckey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            resp.addCookie(tokenService.create(String.valueOf(requiredAccount.getAccountId())
                    , seckey
                    , objectMapper.writeValueAsString(requiredAccount)));
            return Result.ok(Map.of("status", MsgMapping.USERNAME_EDIT_SUCCESS));
        }
        return Result.ok(Map.of("status", MsgMapping.USERNAME_EDIT_ERROR));
    }

}
