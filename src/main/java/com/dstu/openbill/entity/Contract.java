package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Contract")
@Table(name = "CONTRACT")
public class Contract {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name = "NUMBER", nullable = false)
    private String number;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @ManyToMany(mappedBy = "contracts")
    @OnDeleteInverse(DeletePolicy.UNLINK)
    private List<Service> services;

    // ======= Геттеры / Сеттеры =======

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
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

    public List<Service> getServices() {
        return services;
    }
    public void setServices(List<Service> services) {
        this.services = services;
    }

    // ======= Бизнес-валидация =======

    /**
     * Проверяет, что endDate не раньше startDate.
     * Если endDate == null — считается валидным (договор открыт).
     */
    @AssertTrue(message = "Дата окончания не может быть раньше даты начала")
    public boolean isValidDates() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    // ======= Красивые имена для UI и логов =======

    @InstanceName
    @DependsOnProperties({"number", "startDate"})
    @Transient
    public String getDisplayName() {
        return String.format("Договор №%s от %s", number, startDate);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
