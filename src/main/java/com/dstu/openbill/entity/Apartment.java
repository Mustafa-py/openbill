package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Apartment")
@Table(name = "APARTMENT", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_UNQ_APARTMENT_NUMBER_HOUSE", columnNames = {"NUMBER", "HOUSE_ID"})
})
public class Apartment {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    @NotNull
    @Column(name = "AREA", nullable = false)
    private Double area;

    @NotNull
    @Column(name = "ROOMS", nullable = false)
    private Integer rooms;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSE_ID", nullable = false)
    private House house;

    @OneToOne(mappedBy = "apartment", fetch = FetchType.LAZY)
    private Ownership ownership;

    @Column(name = "IS_OCCUPIED")
    private Boolean isOccupied;

    public Boolean getIsOccupied() {
        return isOccupied;
    }

    // --- Getters and setters ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Ownership getOwnership() {
        return ownership;
    }

    public void setOwnership(Ownership ownership) {
        this.ownership = ownership;
    }

    public Boolean getOccupied() {
        return isOccupied;
    }

    public void setOccupied(Boolean occupied) {
        isOccupied = occupied;
    }


    // --- Instance name for UI ---

    @InstanceName
    @DependsOnProperties({"number", "house"})
    @Transient
    public String getDisplayName() {
        if (house != null) {
            return String.format("Кв. %s, %s", number, house.getAddress());
        } else {
            return "Кв. " + number;
        }
    }
}
