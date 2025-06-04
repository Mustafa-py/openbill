package com.dstu.openbill.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
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
@Entity(name = "openbill_Ownership")
@Table(name = "OWNERSHIP", indexes = {
        @Index(name = "IDX_OWNERSHIP_OWNER", columnList = "OWNER_ID"),
        @Index(name = "IDX_OWNERSHIP_APARTMENT", columnList = "APARTMENT_ID"),
        @Index(name = "IDX_OWNERSHIP_START_DATE", columnList = "START_DATE"),
        @Index(name = "IDX_OWNERSHIP_END_DATE", columnList = "END_DATE"),
        @Index(name = "IDX_OWNERSHIP_COMPOSITE", columnList = "APARTMENT_ID, START_DATE, END_DATE") // Для поиска действующих владений
})
public class Ownership {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @NotNull(message = "Владелец обязателен")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_OWNERSHIP_OWNER"))
    private Owner owner;

    @NotNull(message = "Квартира обязательна")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "APARTMENT_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_OWNERSHIP_APARTMENT"))
    private Apartment apartment;

    @NotNull(message = "Дата начала владения обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate; // если null — владеет до сих пор

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

    public Apartment getApartment() {
        return apartment;
    }
    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
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

    // --- Отображение в UI ---
    @InstanceName
    @DependsOnProperties({"owner", "apartment"})
    @Transient
    public String getDisplayName() {
        String ownerName = Optional.ofNullable(owner)
                .map(Owner::getFullName)
                .orElse("—");

        String aptNumber = Optional.ofNullable(apartment)
                .map(Apartment::getNumber)
                .orElse("—");

        return ownerName + " — кв. " + aptNumber;
    }

    // --- Валидация и бизнес-логика ---
    @Transient
    public boolean isActive() {
        return isActiveOn(LocalDate.now());
    }

    @Transient
    public boolean isActiveOn(LocalDate date) {
        return !date.isBefore(startDate) && (endDate == null || !date.isAfter(endDate));
    }

    @PrePersist
    @PreUpdate
    private void validateDates() {
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalStateException("Дата окончания владения не может быть раньше даты начала");
        }
    }

    // --- Equals & HashCode ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ownership ownership = (Ownership) o;
        return Objects.equals(owner, ownership.owner) &&
                Objects.equals(apartment, ownership.apartment) &&
                Objects.equals(startDate, ownership.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, apartment, startDate);
    }
}