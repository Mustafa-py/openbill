package com.dstu.openbill.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Тип начисления (BillingType) для системы биллинга.
 */
public enum BillingType implements EnumClass<String> {
    CHARGE("charge"),         // Обычное начисление
    PENALTY("penalty"),       // Пени
    CORRECTION("correction"), // Перерасчёт
    SUBSIDY("subsidy");       // Субсидия/льгота

    private final String id;

    // Кэш для быстрого поиска по id, игнорируя регистр
    private static final Map<String, BillingType> LOOKUP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(e -> e.getId().toLowerCase(), e -> e));

    BillingType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Получить BillingType по строковому id.
     * @param id строковый идентификатор (регистр не важен)
     * @return BillingType или null, если не найдено
     */
    @Nullable
    public static BillingType fromId(String id) {
        return id == null ? null : LOOKUP.get(id.toLowerCase());
    }
}
