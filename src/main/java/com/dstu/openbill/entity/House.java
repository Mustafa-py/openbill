package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_House")
public class House {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "TOTAL_AREA")
    private Double totalArea;

    @Column(name = "YEAR_BUILT")
    private Integer yearBuilt;

    // ======= Геттеры и сеттеры =======

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Integer getYearBuilt() {
        return yearBuilt;
    }

    public void setYearBuilt(Integer yearBuilt) {
        this.yearBuilt = yearBuilt;
    }
}
