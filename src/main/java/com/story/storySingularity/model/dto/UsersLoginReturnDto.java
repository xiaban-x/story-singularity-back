package com.story.storySingularity.model.dto;

import com.story.storySingularity.model.po.Users;
import lombok.Data;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月03日9:07
 */
@Data
public class UsersLoginReturnDto extends Users {

    //用来区别是注册还是登录
    private String status;
}
