package com.story.storySingularity.unfbx.chatgpt.entity.models;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 *  2023-02-15
 */
@Data
public class ModelResponse implements Serializable {
    private String object;
    private List<Model> data;
}
