package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Billing")
@Table(name = "BILLING", uniqueConstraints = {
        @UniqueConstraint(name = "IDX_UNQ_BILLING", columnNames = {
                "APARTMENT_ID", "TARIFF_ID", "BILLING_DATE"
        })
})
public class Billing {

    @Id
    @GeneratedValue
    private UUID id;

    /** Квартира, для которой начисление */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APARTMENT_ID", nullable = false)
    @OnDelete(DeletePolicy.CASCADE)
    private Apartment apartment;

    /** Тариф, по которому считается сумма */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARIFF_ID", nullable = false)
    @OnDelete(DeletePolicy.CASCADE)
    private Tariff tariff;

    /** Сумма начисления */
    @Column(name = "AMOUNT", nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    /** Дата начисления */
    @Column(name = "BILLING_DATE", nullable = false)
    private LocalDate billingDate;

    // --- getters / setters ---

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
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

    // --- Удобное имя для UI и логов ---
    @InstanceName
    @DependsOnProperties({"apartment", "billingDate", "amount"})
    public String getDisplayName() {
        return String.format("Кв.%s %s: %s",
                apartment != null ? apartment.getNumber() : "—",
                billingDate != null ? billingDate : "",
                amount != null ? amount : "");
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
