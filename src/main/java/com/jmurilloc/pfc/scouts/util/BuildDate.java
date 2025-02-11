package com.jmurilloc.pfc.scouts.util;

import java.util.Calendar;
import java.util.Date;

public abstract class BuildDate {

    private BuildDate(){

    }

    public static Date dateByYear(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,Calendar.JANUARY,1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public static Date dateByYearAndMonth(int year,int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,(month-1),1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public static Date dateByYearAndMonthAndDay(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,(month-1),day,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
}
