package com.story.storySingularity.model.dto;

import lombok.Data;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月01日20:47
 */
@Data
public class UsersSmsLoginDto {
    private String username;
    private String password;
    private String phone;
    private Boolean isPassCode;
}
