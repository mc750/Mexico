package com.mexico750.doacao.user;

import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created by root on 08/06/14.
 */
public class UserHealth {
    private User user;
    private Integer age;
    private Boolean recentCold = Boolean.FALSE;
    private Boolean recentBacteria = Boolean.FALSE;
    private Boolean recentViruses = Boolean.FALSE;
    private Boolean recentVaccine = Boolean.FALSE;
    private Boolean recentCirurgy = Boolean.FALSE;
    private Boolean recentTattoo = Boolean.FALSE;
    private Boolean stdRisk = Boolean.FALSE;
    private Boolean hasHepatits = Boolean.FALSE;
    private Boolean isPregnant = Boolean.FALSE;
    private Boolean recentPregnancy = Boolean.FALSE;
    private Boolean isBreastFedder = Boolean.FALSE;

    public DateTime calculateNextDonation(){
        if (age < 18){
            return user.getBirthday().plusYears(18 - age);
        }

        Period period = new Period().minusYears(100);
        if (recentCold || recentBacteria){
            period = new Period().plusDays(7);
        } if (recentViruses || recentVaccine) {
            period = new Period().plusMonths(1);
        } if (user.getWeight() < 50.0){
            period = new Period().plusMonths(6);
        } if (recentCirurgy || recentTattoo || stdRisk || isBreastFedder || recentPregnancy){
            period = new Period().plusYears(1);
        } if (isPregnant){
            period = new Period().plusYears(1).plusMonths(8);
        } if (hasHepatits){
            period = null;
        }

        if (period == null){
            return null;
        }

        return DateTime.now().plus(period);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRecentCold(Boolean recentCold) {
        this.recentCold = recentCold;
    }

    public void setRecentBacteria(Boolean recentBacteria) {
        this.recentBacteria = recentBacteria;
    }

    public void setRecentCirurgy(Boolean recentCirurgy) {
        this.recentCirurgy = recentCirurgy;
    }

    public void setRecentPregnancy(Boolean recentPregnancy) {
        this.recentPregnancy = recentPregnancy;
    }

    public void setHasHepatits(Boolean hasHepatits) {
        this.hasHepatits = hasHepatits;
    }

    public void setIsBreastFedder(Boolean isBreastFedder) {
        this.isBreastFedder = isBreastFedder;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public void setRecentTattoo(Boolean recentTattoo) {
        this.recentTattoo = recentTattoo;
    }

    public void setRecentVaccine(Boolean recentVaccine) {
        this.recentVaccine = recentVaccine;
    }

    public void setRecentViruses(Boolean recentViruses) {
        this.recentViruses = recentViruses;
    }

    public void setStdRisk(Boolean stdRisk) {
        this.stdRisk = stdRisk;
    }

    public User getUser() {
        return user;
    }
}
