package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_House")
@Table(name = "HOUSE", indexes = {
        @Index(name = "IDX_HOUSE_ADDRESS", columnList = "ADDRESS"),
        @Index(name = "IDX_HOUSE_ASSOCIATION", columnList = "HOUSING_ASSOCIATION_ID")
})
public class House {

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    /** Адрес дома — обязательно и уникально для ассоциации */
    @NotBlank(message = "Адрес обязателен")
    @Column(name = "ADDRESS", nullable = false, length = 200)
    private String address;

    /** Общая площадь дома, только положительное число */
    @NotNull(message = "Общая площадь обязательна")
    @Positive(message = "Площадь должна быть больше 0")
    @Column(name = "TOTAL_AREA", nullable = false)
    private Double totalArea;

    /** Год постройки (опционально) */
    @Min(value = 1800, message = "Год постройки должен быть не ранее 1800")
    @Column(name = "YEAR_BUILT")
    private Integer yearBuilt;

    /** Ассоциация (ТСЖ/УК), обязательно */
    @NotNull(message = "ТСЖ/УК обязательно")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "HOUSING_ASSOCIATION_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_HOUSE_HA"))
    private HousingAssociation housingAssociation;

    /** Квартиры, связанные с домом — удаляются при удалении дома */
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(DeletePolicy.CASCADE)
    private List<Apartment> apartments = new ArrayList<>();

    // ======= Геттеры и сеттеры =======

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getTotalArea() { return totalArea; }
    public void setTotalArea(Double totalArea) { this.totalArea = totalArea; }

    public Integer getYearBuilt() { return yearBuilt; }
    public void setYearBuilt(Integer yearBuilt) { this.yearBuilt = yearBuilt; }

    public HousingAssociation getHousingAssociation() { return housingAssociation; }
    public void setHousingAssociation(HousingAssociation housingAssociation) { this.housingAssociation = housingAssociation; }

    public List<Apartment> getApartments() { return apartments; }
    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments != null ? apartments : new ArrayList<>();
    }

    // ======= Для красивого отображения в UI и для поиска =======

    @InstanceName
    @DependsOnProperties({"address", "housingAssociation"})
    public String getDisplayName() {
        String assoc = housingAssociation != null ? housingAssociation.getName() : "Без ассоциации";
        return String.format("%s (%s)", address != null ? address : "—", assoc);
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    // ======= Equals/hashCode по адресу и ассоциации =======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House house)) return false;
        return Objects.equals(address, house.address)
                && Objects.equals(housingAssociation, house.housingAssociation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, housingAssociation);
    }
}
