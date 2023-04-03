package com.story.storySingularity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.story.storySingularity.model.dto.PostsListDto;
import com.story.storySingularity.model.dto.PostsReturnDto;
import com.story.storySingularity.model.po.Posts;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaban
 * @since 2023-04-01
 */
public interface PostsService extends IService<Posts> {

    Page<PostsReturnDto> listByPostsListDto(PostsListDto postsListDto);

    PostsReturnDto getPostsReturnDtoById(Integer postId);
}
