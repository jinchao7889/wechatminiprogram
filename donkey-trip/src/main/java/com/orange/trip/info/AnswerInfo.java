package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class AnswerInfo {
    @NotNull
    Integer tripId;
    @NotNull
    Long questionId;
    @NotBlank
    String content;
    /**
     * 评论父级ID
     */
    @NotNull
    Integer parentId;

    /**
     * 评论次级ID
     */
    @NotNull
    Integer secondaryId;
}
