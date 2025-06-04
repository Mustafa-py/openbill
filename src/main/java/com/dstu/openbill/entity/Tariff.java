package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.DeletePolicy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Tariff")
@Table(name = "TARIFF")
public class Tariff {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @NotNull(message = "Название тарифа не может быть пустым")
    @Size(min = 1, max = 255, message = "Название тарифа должно содержать от 1 до 255 символов")
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Size(max = 2000, message = "Описание тарифа не должно превышать 2000 символов")
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @NotNull(message = "Тип тарифа не может быть пустым")
    @Enumerated(EnumType.STRING)
    @Column(name = "TARIFF_TYPE", nullable = false, length = 50)
    private TariffType tariffType = TariffType.FIXED;

    @NotNull(message = "Стоимость тарифа не может быть пустой")
    @Column(name = "COST", nullable = false, precision = 19, scale = 2)
    private BigDecimal cost = BigDecimal.ZERO;

    @NotNull(message = "Дата начала действия тарифа не может быть пустой")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Дата окончания действия тарифа не может быть пустой")
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true)
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    private List<ServiceTariff> serviceTariffs;

    // Геттеры и сеттеры
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

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost != null ? cost : BigDecimal.ZERO;
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
        this.active = active != null ? active : true;
    }

    public List<ServiceTariff> getServiceTariffs() {
        return serviceTariffs;
    }

    public void setServiceTariffs(List<ServiceTariff> serviceTariffs) {
        this.serviceTariffs = serviceTariffs;
    }

    @InstanceName
    @DependsOnProperties({"name", "cost", "active"})
    public String getDisplayName() {
        return String.format("%s (%.2f ₽) %s",
                name != null ? name : "—",
                cost != null ? cost : BigDecimal.ZERO,
                Boolean.TRUE.equals(active) ? "" : "[Неактивен]"
        );
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала");
        }
    }
}