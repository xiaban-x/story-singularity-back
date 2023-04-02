package com.story.storySingularity.controller;

import com.story.storySingularity.model.dto.RecordingsReturnDto;
import com.story.storySingularity.model.po.Recordings;
import com.story.storySingularity.service.RecordingsService;
import com.story.storySingularity.service.UsersService;
import com.story.storySingularity.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;

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

    //获取录音表的基本信息
    @GetMapping("/getRecordingsByUserId/{userId}")
    public RestResponse<RecordingsReturnDto> getRecordingsByUserId(@PathVariable("userId") Integer userId){

        RecordingsReturnDto recordingsByUserId = recordingsService.getRecordingsByUserId(userId);
        return RestResponse.success(recordingsByUserId);
    }

    @PostMapping("/saveRecordings")
    public RestResponse<Recordings> saveRecordings(@RequestBody RecordingsReturnDto recordingsReturnDto){
        Recordings recordings = recordingsService.saveRecordings(recordingsReturnDto);
        return RestResponse.success(recordings);
    }


    @DeleteMapping("/deleteRecordings/{userId}")
    public RestResponse<String> deleteRecordings(@PathVariable("userId") Integer userId){
        return RestResponse.success("删除成功");
    }
}
