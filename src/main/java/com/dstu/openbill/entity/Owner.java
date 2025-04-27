package com.dstu.openbill.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Owner")
@Table(name = "OWNER")
public class Owner {

    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @InstanceName
    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Email
    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "owner")
    @OnDelete(DeletePolicy.CASCADE)
    private List<Ownership> ownerships;

    @OneToMany(mappedBy = "owner")
    @OnDelete(DeletePolicy.CASCADE)
    private List<Balance> balances;

    // --- Getters and setters ---

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
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }
    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    public List<Balance> getBalances() {
        return balances;
    }
    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    // --- Display name for UI ---

    @DependsOnProperties("fullName")
    @Transient
    public String getDisplayName() {
        return fullName;
    }
}
