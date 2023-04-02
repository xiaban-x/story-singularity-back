package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.story.storySingularity.mapper.UsersMapper;
import com.story.storySingularity.model.po.Users;
import com.story.storySingularity.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {
    @Autowired
    private UsersMapper usersMapper;
    @Override
    public void saveUser(Users u){
//        u.setId(Integer.valueOf(UUID.randomUUID().toString()));
//        usersMapper.insertUser(u);
        usersMapper.insert(u);
    }

    @Override
    public Users selectUser(Users u) {
        if (u.getUsername() == null || u.getPassword() == null) {
            return null;
        }
        return usersMapper.selectOneUser(u);
    }
    @Override
    public Users selectUserByPhoneNumber(Users u) {
        if (u.getPhone() == null) {
            return null;
        }
        return usersMapper.selectOneUserByPhone(u);
    }
}
