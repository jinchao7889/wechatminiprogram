package com.orange.person.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class AuthenticationDto {
    @NotBlank
    String  userId;
    @NotNull
    Integer auditStatus;
    String auditRemark;
}
