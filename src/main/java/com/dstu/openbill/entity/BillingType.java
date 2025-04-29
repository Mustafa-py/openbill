package com.dstu.openbill.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum BillingType implements EnumClass<String> {

    CHARGE("charge"),       // обычное начисление
    PENALTY("penalty"),     // пени
    CORRECTION("correction"), // перерасчёт
    SUBSIDY("subsidy");     // субсидия/льгота

    private final String id;

    BillingType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /** Преобразует строку в элемент enum, или null если не найден */
    @Nullable
    public static BillingType fromId(String id) {
        for (BillingType type : BillingType.values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}