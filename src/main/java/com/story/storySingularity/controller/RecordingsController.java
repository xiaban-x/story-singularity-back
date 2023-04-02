package com.story.storySingularity.controller;

import com.story.storySingularity.model.dto.RecordingsReturnDto;
import com.story.storySingularity.model.po.Recordings;
import com.story.storySingularity.service.RecordingsService;
import com.story.storySingularity.service.UsersService;
import com.story.storySingularity.util.MultipartFileToFile;
import com.story.storySingularity.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
@RequestMapping("recordings")
public class RecordingsController {

    @Autowired
    private RecordingsService recordingsService;

    @Autowired
    private UsersService usersService;

    //根据用户id获取录音表的基本信息
    @GetMapping("/getRecordingsByUserId/{userId}")
    public RestResponse<List<RecordingsReturnDto>> getRecordingsByUserId(@PathVariable("userId") Integer userId){

        List<RecordingsReturnDto> recordingsByUserId = recordingsService.getRecordingsByUserId(userId);
        return RestResponse.success(recordingsByUserId);
    }
    //保存录音信息
    @PostMapping("/saveRecordings")
    public RestResponse<Recordings> saveRecordings(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam(value = "userId")Integer userId) throws Exception {
        File file1 = MultipartFileToFile.multipartFileToFile(file);
        RecordingsReturnDto recordingsReturnDto = new RecordingsReturnDto();
        recordingsReturnDto.setUserId(userId);
        recordingsReturnDto.setRecordingFile(file1);
        Recordings recordings = recordingsService.saveRecordings(recordingsReturnDto);
        return RestResponse.success(recordings);
    }

    //删除录音信息
    @DeleteMapping("/deleteRecordings/{id}")
    public RestResponse<String> deleteRecordings(@PathVariable("id") Integer id){
        recordingsService.removeById(id);
        return RestResponse.success("删除成功");
    }

    //根据id获取录音信息
    @GetMapping("/getRecordingsById/{id}")
    public RestResponse<RecordingsReturnDto> getRecordingsById(@PathVariable("id") Integer id){
        RecordingsReturnDto recordingsById = recordingsService.getRecordingsById(id);
        return RestResponse.success(recordingsById);
    }
}
