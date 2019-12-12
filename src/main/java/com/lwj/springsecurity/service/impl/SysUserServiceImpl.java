package com.lwj.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwj.springsecurity.dao.SysUserDao;
import com.lwj.springsecurity.entity.SysUser;
import com.lwj.springsecurity.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Override
    public SysUser selectByUsername(String username) {
        return this.baseMapper.selectByUsername(username);
    }
}
