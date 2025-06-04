package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Contract")
@Table(name = "CONTRACT", indexes = {
        @Index(name = "IDX_CONTRACT_OWNER", columnList = "OWNER_ID"),
        @Index(name = "IDX_CONTRACT_START_DATE", columnList = "START_DATE"),
        @Index(name = "IDX_CONTRACT_END_DATE", columnList = "END_DATE")
}, uniqueConstraints = {
        @UniqueConstraint(name = "IDX_UNQ_CONTRACT_NUMBER", columnNames = {"NUMBER"})
})
public class Contract {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @NotNull(message = "Название договора обязательно")
    @Size(min = 1, max = 200, message = "Название договора должно содержать от 1 до 200 символов")
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    @NotNull(message = "Номер договора обязателен")
    @Size(min = 1, max = 50, message = "Номер договора должен содержать от 1 до 50 символов")
    @Column(name = "NUMBER", nullable = false, length = 50)
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

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractServiceTariff> serviceTariffs = new ArrayList<>();

    // Геттеры и сеттеры
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "Название договора обязательно");
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = Objects.requireNonNull(number, "Номер договора обязателен");
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = Objects.requireNonNull(startDate, "Дата начала действия договора обязательна");
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = Objects.requireNonNull(owner, "Владелец договора обязателен");
    }

    public List<ContractServiceTariff> getServiceTariffs() {
        return serviceTariffs;
    }

    public void setServiceTariffs(List<ContractServiceTariff> serviceTariffs) {
        this.serviceTariffs = serviceTariffs;
    }

    // Бизнес-логика
    @AssertTrue(message = "Дата окончания не может быть раньше даты начала")
    public boolean isValidDates() {
        return endDate == null || !endDate.isBefore(startDate);
    }

    @InstanceName
    @DependsOnProperties({"title", "number", "startDate", "owner"})
    public String getDisplayName() {
        return String.format("%s (№%s от %s) — %s",
                Objects.toString(title, "—"),
                Objects.toString(number, "—"),
                startDate != null ? startDate.format(DATE_FORMATTER) : "—",
                owner != null ? owner.getFullName() : "—"
        );
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (!isValidDates()) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract contract)) return false;
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
                ", owner=" + (owner != null ? owner.getId() : "null") +
                '}';
    }

    // Помощник для добавления связи
    public void addServiceTariff(ContractServiceTariff serviceTariff) {
        serviceTariff.setContract(this);
        serviceTariffs.add(serviceTariff);
    }
}