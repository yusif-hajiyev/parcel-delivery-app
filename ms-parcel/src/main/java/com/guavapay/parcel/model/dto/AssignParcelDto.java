package com.guavapay.parcel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignParcelDto {

    @NotNull(message = "The parcel id can't be null")
    private Long parcelId;

    @NotNull(message = "The courier id can't be null")
    private Long courierId;
}
