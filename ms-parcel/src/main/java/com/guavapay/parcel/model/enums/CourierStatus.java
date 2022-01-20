package com.guavapay.parcel.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@AllArgsConstructor
public enum CourierStatus {

    NEW(1),
    IN_EXECUTION(2),
    FREE(3);

    private final Integer status;

    private static final Map<Integer, CourierStatus> VALUES = new ConcurrentHashMap<Integer, CourierStatus>();

    static {
        for (CourierStatus type : CourierStatus.values()) {
            VALUES.put(type.status, type);
        }
    }

    public static CourierStatus getEnum(Integer status) {
        if (status == null)
            return null;
        return VALUES.get(status);
    }
}
