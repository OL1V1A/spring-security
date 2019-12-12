package com.lwj.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId
    private Integer id;

    private String username;

    private String password;

    private String role;

}
