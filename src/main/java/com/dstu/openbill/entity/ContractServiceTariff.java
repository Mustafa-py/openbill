package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_ContractServiceTariff")
@Table(name = "CONTRACT_SERVICE_TARIFF",
        uniqueConstraints = @UniqueConstraint(
                name = "IDX_UNQ_CST_CONTRACT_SERVICE_TARIFF",
                columnNames = {"CONTRACT_ID", "SERVICE_ID", "TARIFF_ID"}
        ),
        indexes = {
                @Index(name = "IDX_CST_CONTRACT", columnList = "CONTRACT_ID"),
                @Index(name = "IDX_CST_SERVICE", columnList = "SERVICE_ID"),
                @Index(name = "IDX_CST_TARIFF", columnList = "TARIFF_ID"),
                @Index(name = "IDX_CST_START_DATE", columnList = "START_DATE"),
                @Index(name = "IDX_CST_END_DATE", columnList = "END_DATE")
        }
)
public class ContractServiceTariff {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    /** Договор */
    @NotNull(message = "Договор обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRACT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CST_CONTRACT"))
    @OnDelete(DeletePolicy.CASCADE)
    private Contract contract;

    /** Услуга */
    @NotNull(message = "Услуга обязательна")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CST_SERVICE"))
    @OnDelete(DeletePolicy.CASCADE)
    private Service service;

    /** Тариф */
    @NotNull(message = "Тариф обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARIFF_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_CST_TARIFF"))
    @OnDelete(DeletePolicy.CASCADE)
    private Tariff tariff;

    /** Начало действия в рамках этого договора */
    @NotNull(message = "Дата начала обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /** Окончание действия */
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // --- Аудит ---
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    private LocalDateTime lastModifiedDate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    private LocalDateTime deletedDate;

    // --- Геттеры и сеттеры ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public Tariff getTariff() { return tariff; }
    public void setTariff(Tariff tariff) { this.tariff = tariff; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getLastModifiedBy() { return lastModifiedBy; }
    public void setLastModifiedBy(String lastModifiedBy) { this.lastModifiedBy = lastModifiedBy; }

    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }

    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }

    public LocalDateTime getDeletedDate() { return deletedDate; }
    public void setDeletedDate(LocalDateTime deletedDate) { this.deletedDate = deletedDate; }

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
    @DependsOnProperties({"contract", "service", "tariff"})
    @jakarta.persistence.Transient
    public String getDisplayName() {
        String contractNumber = Optional.ofNullable(contract)
                .map(Contract::getNumber)
                .orElse("—");

        String serviceName = Optional.ofNullable(service)
                .map(Service::getName)
                .orElse("—");

        String tariffName = Optional.ofNullable(tariff)
                .map(Tariff::getName)
                .orElse("—");

        return String.format("Дог. %s: %s – %s", contractNumber, serviceName, tariffName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContractServiceTariff that)) return false;
        return Objects.equals(contract, that.contract)
                && Objects.equals(service, that.service)
                && Objects.equals(tariff, that.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, service, tariff);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
