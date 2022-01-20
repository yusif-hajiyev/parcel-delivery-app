package com.guavapay.parcel.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParcelNotFoundException extends RuntimeException{

    private final String code;
    private final String message;
}
