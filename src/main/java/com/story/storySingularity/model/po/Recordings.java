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
@TableName("recordings")
public class Recordings implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 录音的用户id
     */
    private Integer userId;

    /**
     * 录音在minio存储的文件位置
     */
    private String url;

    /**
     * 录音转文本后的内容
     */
    private String text;

    /**
     * 录音创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 录音更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime udpateAt;


}
