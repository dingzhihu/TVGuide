package com.howfun.android.tv;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

public class Utils {

	public static final String TV_GUIDE = "TVGuide";

	public static final String AREA_ID = "areaId";
	public static final String STATION_ID = "stationId";
	public static final String CHANNEL_ID = "channelId";

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
}
