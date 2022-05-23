package com.chaconneluo.music.account.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author ChaconneLuo
 */

@Data
@JsonIgnoreProperties({"id","gmtCreate","gmtModified"})
@TableName("t_secret_key")
public class SecretKey {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String appid;

    private String secretKey;

    @TableField(select = false)
    private LocalDateTime gmtCreate;

    @TableField(select = false)
    private LocalDateTime gmtModified;
}
