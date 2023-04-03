package com.story.storySingularity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.story.storySingularity.model.dto.UsersLoginReturnDto;
import com.story.storySingularity.model.po.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaban
 * @since 2023-04-01
 */
public interface UsersService extends IService<Users> {
    void saveUser(Users u);
    Users selectUser(Users u);
    Users selectUserByPhoneNumber(Users u);

    UsersLoginReturnDto login(String phone);
}
