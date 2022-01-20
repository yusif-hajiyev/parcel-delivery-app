package com.guavapay.parcel.model.view;

import com.guavapay.parcel.model.enums.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelView {

    private String parcelCode;
    private Integer quantity;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String addressFrom;
    private String addressTo;
    private String description;
    private ParcelStatus parcelStatus;
    private String createdDate;
    private String cancelDate;
}
