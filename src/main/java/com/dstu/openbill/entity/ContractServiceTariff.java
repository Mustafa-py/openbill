package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_ContractServiceTariff")
@Table(name = "CONTRACT_SERVICE_TARIFF",
        uniqueConstraints = @UniqueConstraint(
                name = "IDX_UNQ_CST_CONTRACT_ST",
                columnNames = {"CONTRACT_ID", "SERVICE_TARIFF_ID"}
        ),
        indexes = {
                @Index(name = "IDX_CST_CONTRACT", columnList = "CONTRACT_ID"),
                @Index(name = "IDX_CST_SERVICE_TARIFF", columnList = "SERVICE_TARIFF_ID")
        }
)
public class ContractServiceTariff {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    /** Договор */
    @NotNull(message = "Договор обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRACT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CST_CONTRACT"))
    @OnDelete(DeletePolicy.CASCADE)
    private Contract contract;

    /** Конкретная услуга с тарифом */
    @NotNull(message = "Услуга и тариф обязательны")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_TARIFF_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CST_SERVICE_TARIFF"))
    @OnDelete(DeletePolicy.CASCADE)
    private ServiceTariff serviceTariff;

    /** Начало действия в рамках этого договора */
    @NotNull(message = "Дата начала обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /** Окончание действия */
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // --- Геттеры и сеттеры ---

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public ServiceTariff getServiceTariff() { return serviceTariff; }
    public void setServiceTariff(ServiceTariff serviceTariff) { this.serviceTariff = serviceTariff; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    // --- Валидация дат ---
    @Transient
    public boolean isValidDates() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (!isValidDates()) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала действия");
        }
    }

    // --- Для удобного отображения в UI ---

    @InstanceName
    @DependsOnProperties({"contract", "serviceTariff"})
    @jakarta.persistence.Transient
    public String getDisplayName() {
        String serviceName = (serviceTariff != null && serviceTariff.getService() != null)
                ? serviceTariff.getService().getName()
                : "—";
        String tariffName = (serviceTariff != null && serviceTariff.getTariff() != null)
                ? serviceTariff.getTariff().getName()
                : "—";
        String contractNumber = contract != null ? contract.getNumber() : "—";
        return String.format("Дог. %s: %s – %s", contractNumber, serviceName, tariffName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContractServiceTariff that)) return false;
        return Objects.equals(contract, that.contract) &&
                Objects.equals(serviceTariff, that.serviceTariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, serviceTariff);
    }

    @Override
    public String toString() {
        return "ContractServiceTariff{" +
                "id=" + id +
                ", contract=" + (contract != null ? contract.getId() : "null") +
                ", serviceTariff=" + (serviceTariff != null ? serviceTariff.getId() : "null") +
                '}';
    }
}
