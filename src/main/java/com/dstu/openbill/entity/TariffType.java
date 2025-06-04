package com.dstu.openbill.entity;

import io.jmix.core.metamodel.datatype.EnumClass;
import org.springframework.lang.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TariffType implements EnumClass<String> {
    PER_AREA("area"),    // За квадратный метр
    FIXED("fixed");      // Фиксированная

    private final String id;

    // Кэш для быстрого поиска по идентификаторам
    private static final Map<String, TariffType> LOOKUP_MAP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            TariffType::getId,
                            Function.identity()
                    ));

    TariffType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nullable
    public static TariffType fromId(String id) {
        if (id == null) return null;
        return LOOKUP_MAP.get(id.toLowerCase());
    }

    // Дополнительный безопасный метод с default значением
    public static TariffType fromIdSafe(String id, TariffType defaultValue) {
        TariffType type = fromId(id);
        return type != null ? type : defaultValue;
    }
}