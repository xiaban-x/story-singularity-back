package com.story.storySingularity.unfbx.chatgpt.entity.chat;

import lombok.Data;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Usage;

import java.io.Serializable;
import java.util.List;

/**
 * 描述： chat答案类
 *
 * @author https:www.unfbx.com
 * 2023-03-02
 */
@Data
public class ChatCompletionResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
}
