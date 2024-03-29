package com.chaconneluo.music.account.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaconneluo.music.account.pojo.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigInteger;

@Mapper
@DS("account")
public interface AccountDao extends BaseMapper<Account> {
    @Select("select max(id) from t_account")
    BigInteger getMaxId();
}
