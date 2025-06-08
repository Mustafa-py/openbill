package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@JmixEntity
@Entity(name = "openbill_Contract")
@Table(name = "CONTRACT", indexes = {
        @Index(name = "IDX_CONTRACT_OWNER", columnList = "OWNER_ID"),
        @Index(name = "IDX_CONTRACT_START_DATE", columnList = "START_DATE"),
        @Index(name = "IDX_CONTRACT_END_DATE", columnList = "END_DATE"),
        @Index(name = "IDX_CONTRACT_NUMBER", columnList = "NUMBER", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_UNQ_CONTRACT_NUMBER", columnNames = {"NUMBER"})
})
public class Contract {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @NotBlank(message = "Название договора обязательно")
    @Size(min = 1, max = 200, message = "Название договора должно содержать от 1 до 200 символов")
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Номер договора обязателен")
    @Size(min = 1, max = 50, message = "Номер договора должен содержать от 1 до 50 символов")
    @Column(name = "NUMBER", nullable = false, length = 50, unique = true)
    private String number;

    @NotNull(message = "Дата начала действия договора обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @FutureOrPresent(message = "Дата окончания не может быть в прошлом")
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @NotNull(message = "Владелец договора обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_CONTRACT_OWNER"))
    private Owner owner;

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

    // --- Связь с ContractServiceTariff ---
    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractServiceTariff> contractServiceTariffs = new ArrayList<>();

    // ======= Геттеры и сеттеры =======
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }

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

    public List<ContractServiceTariff> getContractServiceTariffs() {
        return contractServiceTariffs;
    }
    public void setContractServiceTariffs(List<ContractServiceTariff> contractServiceTariffs) {
        this.contractServiceTariffs = contractServiceTariffs != null ? contractServiceTariffs : new ArrayList<>();
    }

    // ======= Бизнес-логика =======
    @AssertTrue(message = "Дата окончания не может быть раньше даты начала")
    public boolean isValidDates() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    @InstanceName
    @DependsOnProperties({"title", "number", "startDate"})
    @Transient
    public String getDisplayName() {
        return String.format("%s (№%s от %s)",
                Optional.ofNullable(title).orElse("—"),
                Optional.ofNullable(number).orElse("—"),
                Optional.ofNullable(startDate)
                        .map(d -> d.format(DATE_FORMATTER))
                        .orElse("—")
        );
    }

    @Transient
    public String getFullInfo() {
        return getDisplayName() + " — " +
                Optional.ofNullable(owner)
                        .map(Owner::getFullName)
                        .orElse("—");
    }

    @Transient
    public boolean isActive() {
        return isActiveOn(LocalDate.now());
    }

    @Transient
    public boolean isActiveOn(LocalDate date) {
        if (date == null) return false;
        return !date.isBefore(startDate) &&
                (endDate == null || !date.isAfter(endDate));
    }

    // ======= Валидация и жизненный цикл =======
    @PrePersist
    @PreUpdate
    private void validate() {
        if (!isValidDates()) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала");
        }
    }

    // ======= Управление дочерними сущностями (best practice) =======
    public void addContractServiceTariff(ContractServiceTariff cst) {
        if (cst != null && !contractServiceTariffs.contains(cst)) {
            cst.setContract(this);
            contractServiceTariffs.add(cst);
        }
    }

    public void removeContractServiceTariff(ContractServiceTariff cst) {
        if (cst != null && contractServiceTariffs.remove(cst)) {
            cst.setContract(null);
        }
    }

    // ======= Equals & HashCode =======
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(number, contract.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
