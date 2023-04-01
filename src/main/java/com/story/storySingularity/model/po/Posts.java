package com.story.storySingularity.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaban
 */
@Data
@TableName("posts")
public class Posts implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 帖子发布者id
     */
    private Integer userId;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 帖子更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;


}
