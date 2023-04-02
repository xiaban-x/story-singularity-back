package com.story.storySingularity.model.dto;

import lombok.Data;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月01日20:45
 */
@Data
public class UsersLoginDto {
    private String phone;
    private String verifyCode;
}
