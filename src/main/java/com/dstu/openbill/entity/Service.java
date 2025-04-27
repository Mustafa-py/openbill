package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Service")
@Table(name = "SERVICE")
public class Service {

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

    @NotNull
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @ManyToMany(mappedBy = "services")
    private List<Tariff> tariffs;

    @ManyToMany
    @JoinTable(name = "CONTRACT_SERVICE",
            joinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID"))
    private List<Contract> contracts;

    // --- Геттеры и сеттеры ---

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

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    // --- Красивое отображение в UI ---

    @InstanceName
    @DependsOnProperties({"name", "active"})
    public String getDisplayName() {
        return String.format("%s %s",
                name != null ? name : "—",
                Boolean.TRUE.equals(active) ? "" : "[Неактивна]"
        );
    }
}
