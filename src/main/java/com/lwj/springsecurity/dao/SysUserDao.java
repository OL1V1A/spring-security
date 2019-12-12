package com.lwj.springsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lwj.springsecurity.entity.SysUser;

public interface SysUserDao extends BaseMapper<SysUser> {

    SysUser selectByUsername(String username);
}
