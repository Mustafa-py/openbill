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
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Billing")
@Table(name = "BILLING",
        uniqueConstraints = @UniqueConstraint(
                name = "IDX_UNQ_BILLING",
                columnNames = {"CONTRACT_SERVICE_TARIFF_ID", "BILLING_DATE", "TYPE"}
        ),
        indexes = {
                @Index(name = "IDX_BILLING_BILLING_DATE", columnList = "BILLING_DATE"),
                @Index(name = "IDX_BILLING_CONTRACT_SERVICE_TARIFF", columnList = "CONTRACT_SERVICE_TARIFF_ID")
        })
public class Billing {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRACT_SERVICE_TARIFF_ID", nullable = false)
    @OnDelete(DeletePolicy.CASCADE)
    private ContractServiceTariff contractServiceTariff;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private BillingType type = BillingType.CHARGE;

    @NotNull
    @Positive
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @PastOrPresent
    @Column(name = "BILLING_DATE", nullable = false)
    private LocalDate billingDate;

    @Column(name = "COMMENT", length = 1000)
    private String comment;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ContractServiceTariff getContractServiceTariff() {
        return contractServiceTariff;
    }

    public void setContractServiceTariff(ContractServiceTariff contractServiceTariff) {
        this.contractServiceTariff = contractServiceTariff;
    }

    public BillingType getType() {
        return type;
    }

    public void setType(BillingType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(LocalDate billingDate) {
        this.billingDate = billingDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @InstanceName
    @DependsOnProperties({"contractServiceTariff", "billingDate", "amount", "type"})
    @Transient
    public String getDisplayName() {
        String contractNum = Optional.ofNullable(contractServiceTariff)
                .map(ContractServiceTariff::getContract)
                .map(Contract::getNumber)
                .orElse("—");

        // Теперь вытаскиваем имя услуги напрямую из contractServiceTariff
        String serviceName = Optional.ofNullable(contractServiceTariff)
                .map(ContractServiceTariff::getService)
                .map(Service::getName)
                .orElse("—");

        String tariffName = Optional.ofNullable(contractServiceTariff)
                .map(ContractServiceTariff::getTariff)
                .map(Tariff::getName)
                .orElse("—");

        String date = Optional.ofNullable(billingDate)
                .map(LocalDate::toString)
                .orElse("—");

        String amt = Optional.ofNullable(amount)
                .map(a -> String.format("%,.2f ₽", a))
                .orElse("— ₽");

        String typeStr = Optional.ofNullable(type)
                .map(Enum::toString)
                .orElse("—");

        return String.format("Дог.%s | Усл.%s | Тариф.%s | %s [%s]: %s",
                contractNum, serviceName, tariffName, date, typeStr, amt);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
