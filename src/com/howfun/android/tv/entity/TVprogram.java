package com.howfun.android.tv.entity;

public class TVprogram {

	private String mPlayTime = "";
	private String mMeridiem = "";

	private String mProgramInfo = "";
	private String mStationInfo = "";
	
	public TVprogram(String playTime,String meridiem,String programInfo,String stationInfo){
		mPlayTime = playTime;
		mMeridiem = meridiem;
		mProgramInfo = programInfo;
		mStationInfo = stationInfo;
	}
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

	public String toString() {
		return "playTime:" + mPlayTime + ",meridiem:" + mMeridiem
				+ ",programInfo:" + mProgramInfo + ",stationInfo:"
				+ mStationInfo;
	}
}
