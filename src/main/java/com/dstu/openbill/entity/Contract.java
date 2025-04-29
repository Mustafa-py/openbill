package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Contract")
@Table(name = "CONTRACT",
        uniqueConstraints = @UniqueConstraint(
                name = "IDX_UNQ_CONTRACT_NUMBER",
                columnNames = {"NUMBER"}
        ))
public class Contract {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @NotNull
    @Column(name = "NUMBER", nullable = false, length = 50)
    private String number;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    /**
     * Связь с конкретными ServiceTariff из CONTRACT_SERVICE_TARIFF
     */
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractServiceTariff> serviceTariffs;

    // --- getters / setters ---

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
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

    public List<ContractServiceTariff> getServiceTariffs() {
        return serviceTariffs;
    }
    public void setServiceTariffs(List<ContractServiceTariff> serviceTariffs) {
        this.serviceTariffs = serviceTariffs;
    }

    // --- business validation ---

    @AssertTrue(message = "Дата окончания не может быть раньше даты начала")
    @Transient
    public boolean isValidDates() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    // --- human-friendly display ---

    @InstanceName
    @DependsOnProperties({"title", "number", "startDate"})
    @Transient
    public String getDisplayName() {
        return String.format("%s (№%s от %s)",
                title, number, startDate);
    }
}
