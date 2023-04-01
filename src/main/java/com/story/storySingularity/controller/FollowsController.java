package com.story.storySingularity.controller;

import com.story.storySingularity.service.FollowsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
