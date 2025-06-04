package com.dstu.openbill.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Service")
@Table(name = "SERVICE", indexes = {
        @Index(name = "IDX_SERVICE_NAME", columnList = "NAME"),
        @Index(name = "IDX_SERVICE_ACTIVE", columnList = "ACTIVE"),
        @Index(name = "IDX_SERVICE_START_DATE", columnList = "START_DATE"),
        @Index(name = "IDX_SERVICE_END_DATE", columnList = "END_DATE")
})
public class Service {

    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    @NotBlank(message = "Название услуги обязательно")
    @Size(min = 1, max = 255, message = "Название услуги должно содержать от 1 до 255 символов")
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Size(max = 2000, message = "Описание услуги не должно превышать 2000 символов")
    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @NotNull(message = "Дата начала действия услуги обязательна")
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Дата окончания действия услуги обязательна")
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Статус активности обязателен")
    @Column(name = "ACTIVE", nullable = false)
    private Boolean active = true;

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

    // --- Связи ---
    @ManyToMany
    @JoinTable(name = "CONTRACT_SERVICE",
            joinColumns = @JoinColumn(name = "SERVICE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID"),
            foreignKey = @ForeignKey(name = "FK_CONTRACT_SERVICE_SERVICE"),
            inverseForeignKey = @ForeignKey(name = "FK_CONTRACT_SERVICE_CONTRACT"))
    private List<Contract> contracts;

    // ======= Геттеры и сеттеры =======

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) {
        this.active = active != null ? active : true;
    }

    public List<Contract> getContracts() { return contracts; }
    public void setContracts(List<Contract> contracts) { this.contracts = contracts; }

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

    // ======= Бизнес-логика =======

    @InstanceName
    @DependsOnProperties({"name", "active"})
    @Transient
    public String getDisplayName() {
        return String.format("%s%s",
                Optional.ofNullable(name).orElse("—"),
                Boolean.TRUE.equals(active) ? "" : " [Неактивна]"
        );
    }

    @AssertTrue(message = "Дата окончания должна быть после даты начала")
    @Transient
    public boolean isDatesValid() {
        return startDate != null && endDate != null &&
                !endDate.isBefore(startDate);
    }

    @Transient
    public boolean isActiveOn(LocalDate date) {
        return date != null &&
                !date.isBefore(startDate) &&
                !date.isAfter(endDate);
    }

    // ======= Валидация и жизненный цикл =======

    @PrePersist
    @PreUpdate
    private void validate() {
        if (!isDatesValid()) {
            throw new IllegalStateException("Дата окончания не может быть раньше даты начала услуги");
        }
    }

    // ======= Equals & HashCode =======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name) &&
                Objects.equals(startDate, service.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate);
    }
}