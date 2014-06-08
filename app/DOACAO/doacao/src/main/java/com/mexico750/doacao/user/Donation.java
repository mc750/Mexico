package com.mexico750.doacao.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mexico750.doacao.institute.Institute;

import org.joda.time.DateTime;

/**
 * Created by root on 08/06/14.
 */
public class Donation implements Comparable<Donation>{

    private DateTime date;
    private Institute local;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Institute getLocal() {
        return local;
    }

    public void setLocal(Institute local) {
        this.local = local;
    }

    @Override
    public int compareTo(Donation donation) {
        if (date.getMillis() == donation.getDate().getMillis())
            return 0;
        else if (date.getMillis() > donation.getDate().getMillis())
            return 1;

        return -1;
    }
}
