package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_ServiceTariff")
@Table(name = "SERVICE_TARIFF", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_SERVICE_TARIFF_UNQ", columnNames = {"SERVICE_ID", "TARIFF_ID"})
})
public class ServiceTariff {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_ID", nullable = false)
    private Service service;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARIFF_ID", nullable = false)
    private Tariff tariff;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // --- Геттеры/сеттеры ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
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

    // Для отображения в списках и выпадающих
    @Override
    public String toString() {
        return service.getName() + " – " + tariff.getName();
    }
}
