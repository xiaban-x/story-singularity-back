package com.story.storySingularity.model.dto;

import lombok.Data;

import java.io.File;

/**
 * @Description:TODO
 * @author: xiaban
 * @date:2023年04月01日22:36
 */
@Data
public class RecordingsReturnDto {

    /**
     * 录音的用户id
     */
    private Integer userId;

    /**
     * 录音转文本后的内容
     */
    private String text;

    /**
     * 录音文件
     */
    private File recordingFile;
}
