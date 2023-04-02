package com.story.storySingularity.unfbx.chatgpt.entity.moderations;

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
@Builder
@Slf4j
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Moderation implements Serializable {

    @NonNull
    private String input;
    @Builder.Default
    private String model = Model.TEXT_MODERATION_LATEST.getName();

    public void setInput(String input) {
        if (Objects.isNull(input) || "".equals(input)) {
            log.error("input不能为空");
            throw new BaseException(CommonError.PARAM_ERROR);
        }
        this.input = input;
    }

    public void setModel(Model model) {
        if (Objects.isNull(model)) {
            model = Model.TEXT_MODERATION_LATEST;
        }
        this.model = model.getName();
    }

    @Getter
    @AllArgsConstructor
    public enum Model {
        TEXT_MODERATION_STABLE("text-moderation-stable"),
        TEXT_MODERATION_LATEST("text-moderation-latest"),
        ;

        private String name;
    }
}
