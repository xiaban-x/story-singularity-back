package com.story.storySingularity.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.story.storySingularity.model.po.Users;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月03日19:16
 */
@Data
public class PostsReturnDto {
    private static final long serialVersionUID = 1L;
    private Integer id;

    /**
     * 帖子发布者id
     */
    private Users user;

    /**
     * 录音id
     */
    private Integer recordingId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 帖子更新时间
     */
    private LocalDateTime updateAt;
}
