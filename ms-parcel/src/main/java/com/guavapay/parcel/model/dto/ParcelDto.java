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
public class ParcelDto {

    @NotNull(message = "The user id can't be null")
    private Long userId;

    @NotNull(message = "The quantity can't be null")
    private Integer quantity;

    @NotNull(message = "The weight can't be null")
    private Double weight;

    @NotNull(message = "The length can't be null")
    private Double length;

    @NotNull(message = "The width can't be null")
    private Double width;

    @NotNull(message = "The height can't be null")
    private Double height;

    @NotNull(message = "The addressFrom can't be null")
    private AddressDto addressFrom;

    @NotNull(message = "The addressTo can't be null")
    private AddressDto addressTo;

    private String description;
}
