package com.mexico750.doacao.user;

import com.mexico750.doacao.utils.JsonUtils;

import org.joda.time.DateTime;

/**
 * Created by root on 29/05/14.
 */

public class User {

    private String name;
    private String cpf;
    private String email;
    private DateTime birthday;
    private Gender gender;
    private BloodType bloodType;
    private Double weight;
    private DonationHistory donationHistory;
    private UserConfiguration configuration;

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() { return email; }

    public DateTime getBirthday() { return birthday; }

    public Gender getGender() { return gender; }

    public BloodType getBloodType() { return bloodType; }

    public Double getWeight() {
        return weight;
    }

    public DonationHistory getDonationHistory() { return donationHistory; }

    public void setConfiguration(UserConfiguration configuration) { this.configuration = configuration; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(DateTime birthday) { this.birthday = birthday; }

    public void setGender(Gender gender) { this.gender = gender; }

    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setDonationHistory(DonationHistory donationHistory) { this.donationHistory = donationHistory; }

    public UserConfiguration getConfiguration() { return configuration; }

    @Override
    public String toString(){
        return JsonUtils.getJson(this);
    }

}
