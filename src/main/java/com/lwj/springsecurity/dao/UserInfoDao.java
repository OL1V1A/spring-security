package com.lwj.springsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lwj.springsecurity.entity.UserInfoEntity;

/**
 * 用户信息DAO
 */
public interface UserInfoDao extends BaseMapper<UserInfoEntity> {

    IPage<UserInfoEntity> selectUserInfoByGtFraction (IPage<UserInfoEntity> page,Long fraction);
}
