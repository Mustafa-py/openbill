package com.dstu.openbill.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Entity(name = "openbill_Owner")
public class Owner {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "FULL_NAME", nullable = false)
    private String fullName;

    @Column(name = "CONTACTS")
    private String contacts;

    @OneToMany(mappedBy = "owner")
    private List<Apartment> apartments;

    @OneToMany(mappedBy = "owner")
    private List<Balance> balances;

    // Getters and setters
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
}
