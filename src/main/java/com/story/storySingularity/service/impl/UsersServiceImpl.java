package com.story.storySingularity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.story.storySingularity.mapper.UsersMapper;
import com.story.storySingularity.model.dto.UsersLoginReturnDto;
import com.story.storySingularity.model.po.Users;
import com.story.storySingularity.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        return usersMapper.selectOneUser(u);
    }
    @Override
    public Users selectUserByPhoneNumber(Users u) {
        if (u.getPhone() == null) {
            return null;
        }
        return usersMapper.selectOneUserByPhone(u);
    }

    @Override
    public UsersLoginReturnDto login(String phone) {
        UsersLoginReturnDto usersLoginReturnDto = new UsersLoginReturnDto();
        LambdaQueryWrapper<Users> usersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        usersLambdaQueryWrapper.eq(Users::getPhone,phone);
        Users user = usersMapper.selectOne(usersLambdaQueryWrapper);
        if (user == null){
            //用户未注册，需要注册
            Users newUser = new Users();
            newUser.setPhone(phone);
            newUser.setName("用户" + phone.substring(phone.length() - 4));
            int insert = usersMapper.insert(newUser);
            if (insert > 0){
                BeanUtils.copyProperties(newUser,usersLoginReturnDto);
                usersLoginReturnDto.setStatus("注册");
                return usersLoginReturnDto;
            }else{
                return null;
            }
        }
        BeanUtils.copyProperties(user,usersLoginReturnDto);
        usersLoginReturnDto.setStatus("登录");
        return usersLoginReturnDto;
    }
}
