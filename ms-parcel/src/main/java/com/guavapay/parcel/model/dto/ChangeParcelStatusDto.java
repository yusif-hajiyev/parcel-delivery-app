package com.guavapay.parcel.model.dto;

import com.guavapay.parcel.model.enums.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeParcelStatusDto {

    @NotNull(message = "The parcel id can't be null")
    private Long parcelId;

    @NotNull(message = "The parcel status can't be null")
    private ParcelStatus parcelStatus;
}
