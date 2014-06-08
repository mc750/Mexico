package com.mexico750.doacao.user;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by root on 08/06/14.
 */
public class DonationHistory {
    private ArrayList<Donation> donationHistory;

    public Donation getFirst(){
        if (donationHistory.isEmpty()) return null;
        return donationHistory.get(0);
    }

    public Donation getLast(){
        if (donationHistory.isEmpty()) return null;
        return donationHistory.get(donationHistory.size() - 1);
    }

    public ArrayList<Donation> getHistory(){
        if (donationHistory.isEmpty()) return null;
        return donationHistory;
    }

    public void addDonation(Donation donation){
        donationHistory.add(donation);
        Collections.sort(donationHistory);
    }
}
