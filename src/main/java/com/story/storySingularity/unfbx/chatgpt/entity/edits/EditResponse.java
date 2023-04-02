package com.story.storySingularity.unfbx.chatgpt.entity.edits;


import lombok.Data;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Choice;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Usage;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 *  2023-02-15
 */
@Data
public class EditResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
}
