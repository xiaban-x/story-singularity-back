package com.story.storySingularity.unfbx.chatgpt.entity.completions;

import lombok.Data;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Choice;
import com.story.storySingularity.unfbx.chatgpt.entity.common.OpenAiResponse;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Usage;

import java.io.Serializable;

/**
 * 描述： 答案类
 *
 * @author https:www.unfbx.com
 *  2023-02-11
 */
@Data
public class CompletionResponse extends OpenAiResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
}
