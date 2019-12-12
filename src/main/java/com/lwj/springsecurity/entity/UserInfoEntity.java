package com.lwj.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user_info")
public class UserInfoEntity {

    @TableId
    private Long id;

    private String name;

    private Integer age;

    private String skill;

    private String evaluate;

    private Long fraction;
}
