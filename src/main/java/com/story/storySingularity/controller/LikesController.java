package com.story.storySingularity.controller;

import com.story.storySingularity.model.po.Follows;
import com.story.storySingularity.model.po.Likes;
import com.story.storySingularity.service.LikesService;
import com.story.storySingularity.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("likes")
public class LikesController {

    @Autowired
    private LikesService likesService;

    @GetMapping("/list")
    public RestResponse<List<Likes>> list(){

        List<Likes> list = likesService.list();
        return RestResponse.success(list);
    }

    @GetMapping("/getById/{id}")
    public RestResponse<Likes> getById(@PathVariable("id") Integer id){
        Likes bookmarks = likesService.getById(id);
        return RestResponse.success(bookmarks);
    }

    @DeleteMapping("/deleteById/{id}")
    public RestResponse<String> deleteById(@PathVariable("id") Integer id){
        boolean b = likesService.removeById(id);
        if (b){
            return RestResponse.success("删除成功");
        }
        return RestResponse.validfail("删除失败");
    }
}
