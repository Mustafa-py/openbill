package com.dstu.openbill.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
@Entity(name = "openbill_Payment")
@Table(name = "PAYMENT", indexes = {
        @Index(name = "IDX_PAYMENT_DATE", columnList = "PAYMENT_DATE"),
        @Index(name = "IDX_PAYMENT_OWNER", columnList = "OWNER_ID"),
        @Index(name = "IDX_PAYMENT_METHOD", columnList = "PAYMENT_METHOD")
})
public class Payment {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @NotNull(message = "Плательщик обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_PAYMENT_OWNER"))
    private Owner owner;

    @NotNull(message = "Дата платежа обязательна")
    @PastOrPresent(message = "Дата платежа не может быть в будущем")
    @Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDate paymentDate;

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма платежа должна быть положительной")
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull(message = "Способ оплаты обязателен")
    @Column(name = "PAYMENT_METHOD", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

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

    // ======= Геттеры и сеттеры =======

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

    // ======= Отображение записи =======

    @InstanceName
    @DependsOnProperties({"owner", "paymentDate", "amount", "paymentMethod"})
    @Transient
    public String getDisplayName() {
        String name = Optional.ofNullable(owner)
                .map(Owner::getFullName)
                .orElse("—");

        String date = Optional.ofNullable(paymentDate)
                .map(LocalDate::toString)
                .orElse("—");

        String amt = Optional.ofNullable(amount)
                .map(a -> String.format("%,.2f ₽", a))
                .orElse("— ₽");

        String pm = Optional.ofNullable(paymentMethod)
                .map(Enum::name)
                .orElse("—");

        return String.format("%s: %s, %s [%s]", name, amt, date, pm);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    @PrePersist
    @PreUpdate
    private void validate() {
        // Дополнительная валидация, если нужно
    }
}