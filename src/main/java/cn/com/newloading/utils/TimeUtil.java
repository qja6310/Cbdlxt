package cn.com.newloading.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

	/*
	 * Date转String
	 */
	public static String dateToString(Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(time);
		return dateString;
	}

	/*
	 * String转Date
	 */
	public static Date stringToDate(String time,String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 解析时间
	 */
	public static String getDateTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
			time = formatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/*时间转流水号*/
	public static String seqNum() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(new Date());
		return dateString;
	}
	
	/* 判断是否过去日期 */
	public static boolean judgeIsPast(String oldTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date oldDate = sdf.parse(oldTime);
			Date newOld = new Date();
		    newOld = sdf.parse(sdf.format(newOld));
		    int flag = oldDate.compareTo(newOld);
		    if(flag <= 0) {
		    	return true;
		    }else {
		    	return false;
		    }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/*时间格式的转换*/
	public static String timeFormat(String inputDate,String inputFormat,String outputFormat) {
		try {
			DateFormat outFormat = new SimpleDateFormat(outputFormat, Locale.US);
			DateFormat inFormat = new SimpleDateFormat(inputFormat, Locale.US);
			Date date = inFormat.parse(inputDate);
			return outFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
