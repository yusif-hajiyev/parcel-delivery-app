package com.guavapay.parcel.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ChangeCourierStatusRequest {

    @NotNull(message = "The id can't be null or empty")
    private Long id;

    @NotNull(message = "The username can't be null or empty")
    private Integer status;
}
