package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Tariff")
@Table(name = "TARIFF")
public class Tariff {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COST", nullable = false, precision = 19, scale = 2)
    private BigDecimal cost;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @ManyToMany
    @JoinTable(name = "SERVICE_TARIFF",
            joinColumns = @JoinColumn(name = "TARIFF_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID"))
    private List<Service> services;

    // ======= Геттеры и сеттеры =======

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    // ======= Для красивого отображения =======

    @InstanceName
    @DependsOnProperties({"name", "cost", "active"})
    public String getDisplayName() {
        return String.format("%s (%.2f ₽) %s",
                name != null ? name : "—",
                cost != null ? cost : 0.00,
                Boolean.TRUE.equals(active) ? "" : "[Неактивен]"
        );
    }
}
