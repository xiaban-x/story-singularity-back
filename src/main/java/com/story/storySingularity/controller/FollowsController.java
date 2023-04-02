package com.story.storySingularity.controller;

import com.story.storySingularity.model.po.Bookmarks;
import com.story.storySingularity.model.po.Follows;
import com.story.storySingularity.service.FollowsService;
import com.story.storySingularity.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("follows")
public class FollowsController {

    @Autowired
    private FollowsService followsService;

    @GetMapping("/list")
    public RestResponse<List<Follows>> list(){

        List<Follows> list = followsService.list();
        return RestResponse.success(list);
    }

    @GetMapping("/getById/{id}")
    public RestResponse<Follows> getById(@PathVariable("id") Integer id){
        Follows bookmarks = followsService.getById(id);
        return RestResponse.success(bookmarks);
    }

    @DeleteMapping("/deleteById/{id}")
    public RestResponse<String> deleteById(@PathVariable("id") Integer id){
        boolean b = followsService.removeById(id);
        if (b){
            return RestResponse.success("删除成功");
        }
        return RestResponse.validfail("删除失败");
    }
}
