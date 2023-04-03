package com.story.storySingularity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.story.storySingularity.model.dto.PostsListDto;
import com.story.storySingularity.model.dto.PostsReturnDto;
import com.story.storySingularity.model.dto.RecordingsReturnDto;
import com.story.storySingularity.model.po.Posts;
import com.story.storySingularity.service.PostsService;
import com.story.storySingularity.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiaban
 */
@Slf4j
@RestController
@RequestMapping("posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @PostMapping("/savePost")
    public RestResponse<Posts> savePost(@RequestBody Posts post){
        boolean save = postsService.save(post);
        if (save){
            return RestResponse.success(post);
        }
        return RestResponse.validfail("保存失败");
    }

    @DeleteMapping("/deletePost/{postId}")
    public RestResponse<String> deletePost(@PathVariable("postId") Integer postId){
        boolean b = postsService.removeById(postId);
        if (b){
            return RestResponse.success("删除成功");
        }
        return RestResponse.validfail("删除失败");
    }

    @PutMapping("/updatePost")
    public RestResponse<Posts> updatePost(@RequestBody Posts post){
        boolean save = postsService.updateById(post);
        if (save){
            return RestResponse.success(post);
        }
        return RestResponse.validfail("更新失败");
    }
    @PostMapping("/list")
    public RestResponse<Page<PostsReturnDto>> list(@RequestBody PostsListDto postsListDto){
        Page<PostsReturnDto> postsPage = postsService.listByPostsListDto(postsListDto);
        return RestResponse.success(postsPage);
    }

    @GetMapping("/getPost/{postId}")
    public RestResponse<PostsReturnDto> getPostById(@PathVariable("postId") Integer postId){
        PostsReturnDto post = postsService.getPostsReturnDtoById(postId);
        if (post != null){
            return RestResponse.success(post);
        }
        return RestResponse.validfail("没有该帖子");
    }
}
