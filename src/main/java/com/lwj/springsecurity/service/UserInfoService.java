package com.lwj.springsecurity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lwj.springsecurity.dao.UserInfoDao;
import com.lwj.springsecurity.entity.UserInfoEntity;
import org.springframework.stereotype.Service;

/**
 * 用户业务接口
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    IPage<UserInfoEntity> selectUserInfoByGtFraction(IPage<UserInfoEntity> page,Long fraction);
}
