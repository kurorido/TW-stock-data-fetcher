package com.stockparser.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String getNowDateString(){    
		StringBuilder sb = new StringBuilder();
	    Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(System.currentTimeMillis());
	    int year=c.get(Calendar.YEAR)-1911;
	    sb.append(year);
	    int month=c.get(Calendar.MONTH)+1;
	    if(month < 10) {
	    	sb.append(0);
	    }
	    sb.append(month);
	    int day=c.get(Calendar.DAY_OF_MONTH);
	    if(day < 10) {
	    	sb.append(0);
	    }
	    sb.append(day);
	    return sb.toString();
	}
	
	public static String getDateString(Date date){    
		StringBuilder sb = new StringBuilder();
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    int year=c.get(Calendar.YEAR)-1911;
	    sb.append(year);
	    int month=c.get(Calendar.MONTH)+1;
	    if(month < 10) {
	    	sb.append(0);
	    }
	    sb.append(month);
	    int day=c.get(Calendar.DAY_OF_MONTH);
	    if(day < 10) {
	    	sb.append(0);
	    }
	    sb.append(day);
	    return sb.toString();
	}
	
}
