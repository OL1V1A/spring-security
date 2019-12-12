package com.lwj.springsecurity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lwj.springsecurity.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    SysUser selectByUsername(String username);
}
