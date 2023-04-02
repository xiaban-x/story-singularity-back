package com.story.storySingularity.controller;

import com.story.storySingularity.model.po.Bookmarks;
import com.story.storySingularity.service.BookmarksService;
import com.story.storySingularity.util.RestResponse;
import io.swagger.models.auth.In;
import lombok.Getter;
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
@RequestMapping("bookmarks")
public class BookmarksController {

    @Autowired
    private BookmarksService bookmarksService;

    @GetMapping("/list")
    public RestResponse<List<Bookmarks>> list(){

        List<Bookmarks> list = bookmarksService.list();
        return RestResponse.success(list);
    }

    @GetMapping("/getById/{id}")
    public RestResponse<Bookmarks> getById(@PathVariable("id") Integer id){
        Bookmarks bookmarks = bookmarksService.getById(id);
        return RestResponse.success(bookmarks);
    }
    @DeleteMapping("/deleteById/{id}")
    public RestResponse<String> deleteById(@PathVariable("id") Integer id){
        boolean b = bookmarksService.removeById(id);
        if (b){
            return RestResponse.success("删除成功");
        }
        return RestResponse.validfail("删除失败");
    }
}
