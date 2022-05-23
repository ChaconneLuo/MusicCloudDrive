package com.chaconneluo.music.account.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chaconneluo.music.account.serializer.PasswordSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author ChaconneLuo
 */

@Data
@JsonIgnoreProperties({"id", "gmtCreate", "gmtModified"})
@TableName("t_account")
public class Account {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private BigInteger accountId;

    private String email;

    private String username;

    @JsonSerialize(using = PasswordSerializer.class)
    private String password;

    @TableField(select = false)
    private LocalDateTime gmtCreate;

    @TableField(select = false)
    private LocalDateTime gmtModified;

    private Boolean deleted;
}
