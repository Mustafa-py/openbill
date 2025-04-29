package com.dstu.openbill.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;

public enum PaymentMethod implements EnumClass<String> {
    CASH("cash"),
    CARD("card"),
    BANK_TRANSFER("bank_transfer"),
    ONLINE("online");

    private final String id;

    PaymentMethod(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static PaymentMethod fromId(String id) {
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.getId().equals(id)) {
                return pm;
            }
        }
        return null;
    }
}
