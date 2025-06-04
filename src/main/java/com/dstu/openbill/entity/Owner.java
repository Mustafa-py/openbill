package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Owner")
@Table(name = "OWNER", indexes = {
        @Index(name = "IDX_OWNER_FULL_NAME", columnList = "FULL_NAME"),
        @Index(name = "IDX_OWNER_EMAIL", columnList = "EMAIL"),
        @Index(name = "IDX_OWNER_PHONE", columnList = "PHONE_NUMBER")
})
public class Owner {

    private static final String PHONE_REGEX = "^\\+?[0-9\\s()-]{5,20}$";
    private static final String PHONE_MESSAGE = "Номер телефона должен быть в формате +7(XXX)XXX-XXXX";

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @NotBlank(message = "ФИО владельца обязательно")
    @Size(min = 3, max = 100, message = "ФИО должно содержать от 3 до 100 символов")
    @Column(name = "FULL_NAME", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Номер телефона обязателен")
    @Pattern(regexp = PHONE_REGEX, message = PHONE_MESSAGE)
    @Size(max = 20, message = "Номер телефона не должен превышать 20 символов")
    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Некорректный формат email")
    @Size(max = 50, message = "Email не должен превышать 50 символов")
    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ownership> ownerships = new ArrayList<>();

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "owner")
    private List<Balance> balances = new ArrayList<>();

    // Геттеры и сеттеры
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = Objects.requireNonNull(fullName, "ФИО владельца обязательно");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "Номер телефона обязателен");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email, "Email обязателен");
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships != null ? ownerships : new ArrayList<>();
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances != null ? balances : new ArrayList<>();
    }

    @InstanceName
    @DependsOnProperties("fullName")
    public String getDisplayName() {
        return fullName != null ? fullName : "Безымянный владелец";
    }

    // Методы для работы с коллекциями
    public void addOwnership(Ownership ownership) {
        ownership.setOwner(this);
        ownerships.add(ownership);
    }

    public void removeOwnership(Ownership ownership) {
        ownerships.remove(ownership);
        ownership.setOwner(null);
    }

    // equals и hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner owner)) return false;
        return Objects.equals(email, owner.email) &&
                Objects.equals(phoneNumber, owner.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phoneNumber);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Дополнительные бизнес-методы
    public String getContactInfo() {
        return String.format("Тел.: %s | Email: %s", phoneNumber, email);
    }
}