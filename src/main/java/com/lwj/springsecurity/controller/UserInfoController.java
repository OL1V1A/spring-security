package com.lwj.springsecurity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lwj.springsecurity.entity.UserInfoEntity;
import com.lwj.springsecurity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/getInfo")
    public UserInfoEntity getInfo(String userId){
        UserInfoEntity entity = userInfoService.getById(userId);
        return entity;
    }

    @RequestMapping("/getList")
    public List<UserInfoEntity> getList(){
        List<UserInfoEntity> list = userInfoService.list();
        return list;
    }

    @RequestMapping("/getInfoListPage")
    public IPage<UserInfoEntity> getInfoListPage(){
        IPage<UserInfoEntity> page = new Page<>();
        page.setCurrent(5);
        page.setSize(1);
        page = userInfoService.page(page);
        return page;
    }

    @RequestMapping("/getListMap")
    public Collection<UserInfoEntity> getListMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("age",20);
        Collection<UserInfoEntity> list = userInfoService.listByMap(map);
        return list;
    }

    @RequestMapping("/saveInfo")
    public void saveInfo(){
        UserInfoEntity entity = new UserInfoEntity();
        entity.setName("小龙");
        entity.setSkill("Java");
        entity.setAge(18);
        entity.setFraction(59L);
        entity.setEvaluate("这是一个改BUG的码农");
        userInfoService.save(entity);
    }

    @RequestMapping("/saveInfoList")
    public void saveInfoList(){
        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            UserInfoEntity entity = new UserInfoEntity();
            entity.setName("List" + i);
            entity.setSkill("睡觉" + i);
            entity.setAge(18 + i);
            entity.setFraction(Long.valueOf(60 + i));
            entity.setEvaluate("这家伙一天吃" + i + "顿饭");
            list.add(entity);
        }
        userInfoService.saveBatch(list);
    }

    @RequestMapping("/updateInfo")
    public void updateInfo(){
        UserInfoEntity entity = new UserInfoEntity();
        entity.setId(1L);
        entity.setAge(99);
        userInfoService.updateById(entity);
    }

    @RequestMapping("/deleteInfo")
    public void deleteInfo(String userId){
        userInfoService.removeById(userId);
    }

    @RequestMapping("/deleteInfoList")
    public void deleteInfoList(){
        List<String> userIdList = new ArrayList<>();
        userIdList.add("12");
        userIdList.add("13");
        userInfoService.removeByIds(userIdList);
    }

    @RequestMapping("/deleteInfoMap")
    public void deleteInfoMap(){
        //kay是字段名 value是字段值
        Map<String,Object> map = new HashMap<>();
        map.put("skill","删除");
        map.put("fraction",10L);
        userInfoService.removeByMap(map);
    }

    @RequestMapping("/getInfoLike")
    public IPage<UserInfoEntity> getInfoLike(){
        Map<String,Object> map = new HashMap<>();
        IPage<UserInfoEntity> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().isNotNull(UserInfoEntity::getEvaluate);
        page = userInfoService.page(page,wrapper);
        return page;
    }

    @RequestMapping("/getInfoListSQL")
    public IPage<UserInfoEntity> getInfoListSQL(){
        IPage<UserInfoEntity> page = new Page<>();
        page.setCurrent(1);
        page.setSize(5);
        page = userInfoService.selectUserInfoByGtFraction(page, 60L);
        return page;
    }

}
