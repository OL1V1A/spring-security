package com.lwj.springsecurity.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lwj.springsecurity.dao.UserInfoDao;
import com.lwj.springsecurity.entity.UserInfoEntity;
import com.lwj.springsecurity.service.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService{
    @Override
    public IPage<UserInfoEntity> selectUserInfoByGtFraction(IPage<UserInfoEntity> page, Long fraction) {
        return this.baseMapper.selectUserInfoByGtFraction(page,fraction);
    }
}
