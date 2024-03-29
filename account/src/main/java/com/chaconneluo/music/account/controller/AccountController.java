package com.chaconneluo.music.account.controller;

import com.chaconneluo.music.account.common.Const;
import com.chaconneluo.music.account.common.MsgMapping;
import com.chaconneluo.music.account.pojo.Account;
import com.chaconneluo.music.account.service.AccountService;
import com.chaconneluo.music.account.service.SecretKeyService;
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
    private final SecretKeyService secretKeyService;

    @PostMapping("/register")
    public Result<Map<String, String>> register(@RequestBody Account account, HttpServletResponse resp) throws JsonProcessingException {

        var registerStatus = accountService.insert(account);
        if (registerStatus) {
            login(account, resp);
            return Result.ok().build();
        } else {
            return Result.error(MsgMapping.EMAIL_REPEAT);
        }
    }

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Account account, HttpServletResponse resp) throws JsonProcessingException {
        var loginSuccess = accountService.check(account);
        if (loginSuccess) {
            var secretKey = secretKeyService.getKey(Const.APPID);
            if (secretKey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            resp.addCookie(tokenService.create(String.valueOf(account.getEmail())
                    , secretKey
                    , objectMapper.writeValueAsString(account)));
            tokenService.writeSecretKey(Const.APPID, secretKey);
            return Result.ok().build();
        } else {
            return Result.error(MsgMapping.LOGIN_ERROR);
        }
    }

    @PostMapping("/editPassword")
    public Result<Map<String, String>> editPassword(@RequestParam("email") String email,
                                                    @RequestParam("oldPassword") String oldPassword,
                                                    @RequestParam("newPassword") String newPassword,
                                                    @RequestHeader("token") String token) {
        var updateSuccess = accountService.updatePassword(email, oldPassword, newPassword);
        if (updateSuccess) {
            var secretKey = secretKeyService.getKey(Const.APPID);
            if (secretKey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            tokenService.deleteAllToken(token,secretKey);
            return Result.ok().build();
        }
        return Result.error(MsgMapping.PASSWORD_EDIT_ERROR);
    }

    @PostMapping("/editUsername")
    public Result<Map<String, String>> editUsername(@RequestParam("email") String email,
                                                    @RequestParam("username") String newUsername,
                                                    @RequestHeader("token") String token,
                                                    HttpServletResponse resp) throws JsonProcessingException {
        var requiredAccount = accountService.updateUsername(email, newUsername);
        if (requiredAccount != null) {
            var seckey = secretKeyService.getKey(Const.APPID);
            if (seckey.equals("")) {
                log.error("Secret Key Query Error");
                return Result.error().msg("Server Error").build();
            }
            resp.addCookie(tokenService.create(String.valueOf(requiredAccount.getAccountId())
                    , seckey
                    , objectMapper.writeValueAsString(requiredAccount)));
            return Result.ok().build();
        }
        return Result.error(MsgMapping.USERNAME_EDIT_ERROR);
    }

}
