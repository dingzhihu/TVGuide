package com.howfun.android.tv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class Utils {

	public static final String TV_GUIDE = "TVGuide";
	
	public static final int MSG_NETWORK_ERROR = 1;
	public static final int MSG_GUIDE_UPDATED = 2;

	public static final String AREA_ID = "areaId";
	public static final String STATION_ID = "stationId";
	public static final String CHANNEL_ID = "channelId";
	public static final String PROGRAM_DATE = "programDate";

	public static final String AM = "AM";
	public static final String PM = "PM";
	public static final String NAN = "NAN";

	public static void log(String tag, String info) {
		Log.d(TV_GUIDE + "============>" + tag, "-------->" + info);
	}

	public static void showMessageDlg(Context context, String msg) {
		new AlertDialog.Builder(context).setMessage(msg).setPositiveButton(
				android.R.string.ok, null).show();
	}
	
	/**
	 * 
	 * @param offset the date offsets today
	 * @return 
	 */
	public static String getDate(int offset){
		String dateStr = "";
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, offset);
		Date date=c.getTime();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		dateStr=df.format(date);
		return dateStr;
		
	}
}
