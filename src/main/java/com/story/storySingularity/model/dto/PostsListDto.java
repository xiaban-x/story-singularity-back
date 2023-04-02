package com.story.storySingularity.model.dto;

import lombok.Data;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月02日11:25
 */
@Data
public class PostsListDto {

    //页码
    private Integer pageNo;
    //一次查询多少个
    private Integer pageNum;
    //查询内容
    private String key;
}

