package com.story.storySingularity.unfbx.chatgpt.entity.embeddings;

import lombok.Data;
import com.story.storySingularity.unfbx.chatgpt.entity.common.Usage;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 *  2023-02-15
 */
@Data
public class EmbeddingResponse implements Serializable {

    private String object;
    private List<Item> data;
    private String model;
    private Usage usage;
}
