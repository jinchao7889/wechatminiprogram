package com.orange.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCommentDto {
    @NotBlank
    String orderId;
    @NotEmpty
    List<String> imgUrl;
    @NotNull
    String commentContent;
}
