package com.chaconneluo.music.account.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaconneluo.music.account.pojo.SecretKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("seckey")
public interface SeckeyDao extends BaseMapper<SecretKey> {
}
