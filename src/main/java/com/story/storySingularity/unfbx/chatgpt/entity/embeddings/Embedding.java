package com.story.storySingularity.unfbx.chatgpt.entity.embeddings;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import com.story.storySingularity.unfbx.chatgpt.exception.BaseException;
import com.story.storySingularity.unfbx.chatgpt.exception.CommonError;

import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：
 *
 * @author https:www.unfbx.com
 *  2023-02-15
 */
@Getter
@Slf4j
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Embedding implements Serializable {
    @NonNull
    @Builder.Default
    private String model = Model.TEXT_EMBEDDING_ADA_002.getName();
    /**
     * 必选项：长度不能超过：8192
     */
    @NonNull
    private String input;

    private String user;

    public void setModel(Model model) {
        if (Objects.isNull(model)) {
            model = Model.TEXT_EMBEDDING_ADA_002;
        }
        this.model = model.getName();
    }

    public void setInput(String input) {
        if (input == null || "".equals(input)) {
            log.error("input不能为空");
            throw new BaseException(CommonError.PARAM_ERROR);
        }
        if (input.length() > 8192) {
            log.error("input超长");
            throw new BaseException(CommonError.PARAM_ERROR);
        }
        this.input = input;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Getter
    @AllArgsConstructor
    public enum Model {
        TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
        ;
        private String name;
    }
}
