package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Ownership")
@Table(name = "OWNERSHIP")
public class Ownership {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Owner owner;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APARTMENT_ID", nullable = false, unique = true)
    private Apartment apartment;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate; // если null — владеет до сих пор

    // --- Геттеры и сеттеры ---

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Apartment getApartment() {
        return apartment;
    }
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }


    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // --- Отображение в UI ---

    @InstanceName
    @DependsOnProperties({"owner", "apartment"})
    @Transient
    public String getDisplayName() {
        if (owner != null && apartment != null) {
            return owner.getFullName() + " — кв. " + apartment.getNumber();
        } else {
            return "";
        }
    }
}
