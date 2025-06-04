package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_HousingAssociation")
@Table(name = "HOUSING_ASSOCIATION", indexes = {
        @Index(name = "IDX_HOUSING_ASSOCIATION_NAME", columnList = "NAME"),
        @Index(name = "IDX_HOUSING_ASSOCIATION_INN", columnList = "INN")
})
public class HousingAssociation {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    /** Наименование ТСЖ/УК */
    @NotBlank(message = "Наименование обязательно")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    /** ИНН (12 цифр для юрлица, 10 для ИП/организаций) */
    @NotBlank(message = "ИНН обязателен")
    @Pattern(regexp = "\\d{10}|\\d{12}", message = "ИНН должен содержать 10 или 12 цифр")
    @Column(name = "INN", nullable = false, length = 12)
    private String inn;

    /** Юридический или фактический адрес */
    @NotBlank(message = "Адрес обязателен")
    @Size(max = 200, message = "Адрес не должен превышать 200 символов")
    @Column(name = "ADDRESS", nullable = false, length = 200)
    private String address;

    /** Контактный телефон (международный формат, можно +7(XXX)XXX-XX-XX) */
    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^\\+?[0-9\\s()-]{7,20}$", message = "Неверный формат телефона")
    @Column(name = "PHONE", nullable = false, length = 20)
    private String phone;

    /** Дома, связанные с ТСЖ/УК. Удаляются каскадно при удалении ассоциации */
    @OneToMany(mappedBy = "housingAssociation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(DeletePolicy.CASCADE)
    private List<House> houses = new ArrayList<>();

    // --- Геттеры и сеттеры ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInn() { return inn; }
    public void setInn(String inn) { this.inn = inn; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<House> getHouses() { return houses; }
    public void setHouses(List<House> houses) {
        this.houses = houses != null ? houses : new ArrayList<>();
    }

    // --- Красивое отображение для UI и поиска ---
    @InstanceName
    @DependsOnProperties({"name", "inn"})
    public String getDisplayName() {
        return String.format("%s (ИНН: %s)", name != null ? name : "—", inn != null ? inn : "—");
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    // --- Equals/hashCode по ИНН (уникальный идентификатор для юрлиц) ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HousingAssociation that)) return false;
        return Objects.equals(inn, that.inn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inn);
    }
}
