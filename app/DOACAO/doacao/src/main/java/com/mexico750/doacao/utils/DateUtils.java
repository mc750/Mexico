package com.mexico750.doacao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by root on 07/06/14.
 */
public class DateUtils {
    private static DateFormat SIMPLE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    public static GregorianCalendar getDateFrom(int year, int month, int day){
        return new GregorianCalendar(year, month, day);
    }

    public static String toStr(Calendar cal){
        return SIMPLE_FORMATTER.format(cal);
    }
}
