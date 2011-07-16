package com.howfun.android.tv.entity;

public class TVprogram {

	public static final String AM = "AM";
	public static final String PM = "PM";

	private String mPlayTime = "";
	private String mMeridiem = "";

	private String mProgramInfo = "";
	private String mStationInfo = "";

	public String getPlayTime() {
		return mPlayTime;
	}

	public void setPlayTime(String playTime) {
		mPlayTime = playTime;
	}

	public String getMeridiem() {
		return mMeridiem;
	}

	public void setMeridiem(String meridiem) {
		mMeridiem = meridiem;
	}

	public String getProgramInfo() {
		return mProgramInfo;
	}

	public void setProgramInfo(String programInfo) {
		this.mProgramInfo = programInfo;
	}

	public String getStationInfo() {
		return mStationInfo;
	}

	public void setStationInfo(String stationInfo) {
		this.mStationInfo = stationInfo;
	}
}
