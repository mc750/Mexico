package com.mexico750.doacao.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by root on 07/06/14.
 */
public class DateUtils {
    private static final DateTimeFormatter SIMPLE_DTF = DateTimeFormat.forPattern("dd/MM/yyyy");

    public static DateTime getDateFor(int year, int month, int day){
        return new DateTime(year, month, day, 0, 0);
    }

    public static DateTime parseDate(String date){
        return SIMPLE_DTF.parseDateTime(date);
    }

    public static String toStr(DateTime dt){
        return dt.toString(SIMPLE_DTF);
    }
}
