package com.dstu.openbill.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Способы оплаты платежа.
 */
public enum PaymentMethod implements EnumClass<String> {
    CASH("cash"),           // Наличные
    CARD("card"),           // Банковская карта
    BANK_TRANSFER("bank_transfer"), // Банковский перевод
    ONLINE("online");       // Онлайн-сервисы

    private final String id;

    private static final Map<String, PaymentMethod> LOOKUP =
            Arrays.stream(values()).collect(Collectors.toMap(e -> e.getId().toLowerCase(), e -> e));

    PaymentMethod(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static PaymentMethod fromId(String id) {
        return id == null ? null : LOOKUP.get(id.toLowerCase());
    }
}
