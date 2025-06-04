package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_ServiceTariff")
@Table(name = "SERVICE_TARIFF", indexes = {
        @Index(name = "IDX_SERVICE_TARIFF_TARIFF", columnList = "TARIFF_ID"),
        @Index(name = "IDX_SERVICE_TARIFF_SERVICE", columnList = "SERVICE_ID"),
        @Index(name = "IDX_SERVICE_TARIFF_UNIQUE", columnList = "TARIFF_ID, SERVICE_ID", unique = true)
})
public class ServiceTariff {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @NotNull(message = "Связь с тарифом обязательна")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARIFF_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SERVICE_TARIFF_TARIFF"))
    @OnDelete(DeletePolicy.CASCADE)
    private Tariff tariff;

    @NotNull(message = "Связь с услугой обязательна")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SERVICE_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_SERVICE_TARIFF_SERVICE"))
    @OnDelete(DeletePolicy.CASCADE)
    private Service service;

    // --- Сюда легко добавить новые поля: комментарии, даты, коэффициенты и др.

    // Геттеры и сеттеры
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(@NotNull Tariff tariff) {
        this.tariff = Objects.requireNonNull(tariff, "Tariff не может быть null");
    }

    public Service getService() {
        return service;
    }

    public void setService(@NotNull Service service) {
        this.service = Objects.requireNonNull(service, "Service не может быть null");
    }

    @InstanceName
    @DependsOnProperties({"service", "tariff"})
    public String getDisplayName() {
        String serviceName = service != null && service.getName() != null ? service.getName() : "Услуга не выбрана";
        String tariffName = tariff != null && tariff.getName() != null ? tariff.getName() : "Тариф не выбран";
        return serviceName + " — " + tariffName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceTariff that)) return false;
        return Objects.equals(tariff, that.tariff) &&
                Objects.equals(service, that.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tariff, service);
    }

    @Override
    public String toString() {
        return "ServiceTariff{" +
                "id=" + id +
                ", tariff=" + (tariff != null ? tariff.getId() : "null") +
                ", service=" + (service != null ? service.getId() : "null") +
                '}';
    }
}
