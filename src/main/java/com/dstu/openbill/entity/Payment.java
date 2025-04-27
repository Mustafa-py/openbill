package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Payment")
@Table(name = "PAYMENT")
public class Payment {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private Owner owner;

    @Column(name = "PAYMENT_DATE", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // ======= Отображение записи =======

    @InstanceName
    @DependsOnProperties({"owner", "paymentDate", "amount"})
    public String getDisplayName() {
        return String.format("%s: %s₽ (%s)",
                owner != null ? owner.getFullName() : "—",
                amount != null ? amount : "—",
                paymentDate != null ? paymentDate : "—");
    }
}
