package com.orange.tavels.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class CommentInfo {
    @NotNull
    String travelsId;

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

    String toAnswerUserId;
}
