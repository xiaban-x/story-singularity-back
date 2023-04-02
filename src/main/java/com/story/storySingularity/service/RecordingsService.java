package com.story.storySingularity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.story.storySingularity.model.dto.RecordingsReturnDto;
import com.story.storySingularity.model.po.Recordings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaban
 * @since 2023-04-01
 */
public interface RecordingsService extends IService<Recordings> {

    List<RecordingsReturnDto> getRecordingsByUserId(Integer userId);

    public File downloadFileFromMinIO(String bucket, String objectName);

    Recordings saveRecordings(RecordingsReturnDto recordingsReturnDto);

    RecordingsReturnDto getRecordingsById(Integer id);

//    ArrayList<RecordingsReturnDto> listPage(Integer pageNo, Integer pageNum);
}
