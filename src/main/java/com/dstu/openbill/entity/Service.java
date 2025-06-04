package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Service")
@Table(name = "SERVICE")
public class Service {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @NotNull(message = "Название услуги обязательно")
    @Size(min = 1, max = 255, message = "Название услуги должно содержать от 1 до 255 символов")
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Size(max = 2000, message = "Описание услуги не должно превышать 2000 символов")
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @NotNull(message = "Дата начала действия услуги обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Дата окончания действия услуги обязательна")
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

    // --- Связь с ServiceTariff (главный способ связки с тарифами) ---
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceTariff> serviceTariffs;

    // --- Связь с контрактами (оставил ManyToMany как было) ---
    @ManyToMany
    @JoinTable(name = "CONTRACT_SERVICE",
            joinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID"))
    private List<Contract> contracts;

    // --- Геттеры и сеттеры ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active != null ? active : true; }

    public List<ServiceTariff> getServiceTariffs() { return serviceTariffs; }
    public void setServiceTariffs(List<ServiceTariff> serviceTariffs) { this.serviceTariffs = serviceTariffs; }

    public List<Contract> getContracts() { return contracts; }
    public void setContracts(List<Contract> contracts) { this.contracts = contracts; }

    // --- Красивое отображение в UI ---
    @InstanceName
    @DependsOnProperties({"name", "active"})
    public String getDisplayName() {
        return String.format("%s %s",
                name != null ? name : "—",
                Boolean.TRUE.equals(active) ? "" : "[Неактивна]"
        );
    }

    // --- Проверка дат ---
    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала услуги");
        }
    }
}
