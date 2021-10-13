package com.orange.trip.info;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class TripQuestionsInfo {
    @NotNull
    Integer tripId;
    @NotBlank
    String question;
}
