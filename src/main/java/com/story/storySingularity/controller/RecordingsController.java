package com.story.storySingularity.controller;

import com.story.storySingularity.service.RecordingsService;
import com.story.storySingularity.service.UsersService;
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
@RequestMapping("recordings")
public class RecordingsController {

    @Autowired
    private RecordingsService recordingsService;

    @Autowired
    private UsersService usersService;


}
