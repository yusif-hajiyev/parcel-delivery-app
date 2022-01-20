package com.guavapay.parcel.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    USER_NOT_FOUND("exception.user-not-found", "user not found"),
    PARCEL_NOT_FOUND("exception.parcel-not-found", "parcel not found"),
    COURIER_NOT_FOUND("exception.courier-not-found", "courier not found"),
    CLIENT_ERROR("exception.client-service-failed", "client service failed");

    private final String code;
    private final String message;
}
