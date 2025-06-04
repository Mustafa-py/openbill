package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Apartment")
@Table(name = "APARTMENT", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_UNQ_APARTMENT_NUMBER_HOUSE", columnNames = {"NUMBER", "HOUSE_ID"})
})
public class Apartment {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    /** Номер квартиры, обязателен, не более 20 символов */
    @NotBlank(message = "Номер квартиры обязателен")
    @Size(max = 20, message = "Номер квартиры не должен превышать 20 символов")
    @Column(name = "NUMBER", nullable = false, length = 20)
    private String number;

    /** Площадь, только положительное значение */
    @NotNull(message = "Площадь обязательна")
    @Positive(message = "Площадь должна быть больше 0")
    @Column(name = "AREA", nullable = false)
    private Double area;

    /** Количество комнат, минимум 1 */
    @NotNull(message = "Количество комнат обязательно")
    @Min(value = 1, message = "В квартире должна быть хотя бы одна комната")
    @Column(name = "ROOMS", nullable = false)
    private Integer rooms;

    /** Дом, к которому относится квартира */
    @NotNull(message = "Дом обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_APARTMENT_HOUSE"))
    private House house;

    /** Текущее владение квартирой (null — если не назначено) */
    @OneToOne(mappedBy = "apartment", fetch = FetchType.LAZY)
    private Ownership ownership;

    /** Признак занятости (true — занята, false — свободна, null — неизвестно) */
    @Column(name = "IS_OCCUPIED")
    private Boolean isOccupied;

    // --- Геттеры и сеттеры ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }

    public Integer getRooms() { return rooms; }
    public void setRooms(Integer rooms) { this.rooms = rooms; }

    public House getHouse() { return house; }
    public void setHouse(House house) { this.house = house; }

    public Ownership getOwnership() { return ownership; }
    public void setOwnership(Ownership ownership) { this.ownership = ownership; }

    public Boolean getIsOccupied() { return isOccupied; }
    public void setIsOccupied(Boolean isOccupied) { this.isOccupied = isOccupied; }

    // --- Красивое отображение для UI и логики ---

    @InstanceName
    @DependsOnProperties({"number", "house"})
    @Transient
    public String getDisplayName() {
        String address = house != null ? house.getAddress() : "Неизвестный дом";
        return String.format("Кв. %s, %s", number != null ? number : "—", address);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    // --- equals и hashCode по уникальному бизнес-ключу (number + house) ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Apartment that)) return false;
        return Objects.equals(number, that.number) && Objects.equals(house, that.house);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, house);
    }
}
