package com.acme.bank.loan.entitlement.domain.event;

import com.acme.bank.loan.entitlement.domain.model.Country;
import com.acme.bank.loan.entitlement.domain.model.Gender;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public class EnrichLoanEvent {

    private UUID uuid;
    private String personalId;
    private LocalDate birthDate;
    private Gender gender;
    private String firstName;
    private String lastName;
    private Country nationality;
    private ZonedDateTime enrichedTimestamp;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public ZonedDateTime getEnrichedTimestamp() {
        return enrichedTimestamp;
    }

    public void setEnrichedTimestamp(ZonedDateTime enrichedTimestamp) {
        this.enrichedTimestamp = enrichedTimestamp;
    }
}
