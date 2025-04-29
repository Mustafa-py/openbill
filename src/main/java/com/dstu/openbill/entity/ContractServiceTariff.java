package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_ContractServiceTariff")
@Table(name = "CONTRACT_SERVICE_TARIFF",
        uniqueConstraints = @UniqueConstraint(
                name = "IDX_UNQ_CST_CONTRACT_ST",
                columnNames = {"CONTRACT_ID", "SERVICE_TARIFF_ID"}
        ))
public class ContractServiceTariff {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    /** Договор */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CONTRACT_ID", nullable = false)
    @OnDelete(DeletePolicy.CASCADE)
    private Contract contract;

    /** Конкретная услуга с тарифом */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_TARIFF_ID", nullable = false)
    @OnDelete(DeletePolicy.CASCADE)
    private ServiceTariff serviceTariff;

    /** Начало действия в рамках этого договора */
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    /** Окончание действия */
    @Column(name = "END_DATE")
    private LocalDate endDate;

    // --- Getters / Setters ---

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public ServiceTariff getServiceTariff() {
        return serviceTariff;
    }
    public void setServiceTariff(ServiceTariff serviceTariff) {
        this.serviceTariff = serviceTariff;
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

    // --- Для удобного отображения в UI ---

    @InstanceName
    @DependsOnProperties({"contract", "serviceTariff"})
    @jakarta.persistence.Transient
    public String getDisplayName() {
        String svc = serviceTariff != null
                ? serviceTariff.getService().getName()
                + " – " + serviceTariff.getTariff().getName()
                : "—";
        String ctr = contract != null
                ? contract.getNumber()
                : "—";
        return String.format("Дог. %s: %s", ctr, svc);
    }
}
