package com.orange.share.page;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class PageInfo {

    @NotNull
    Integer page;
    @NotNull
    Integer size;
    Integer condition;
}
