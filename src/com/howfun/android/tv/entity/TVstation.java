package com.howfun.android.tv.entity;

public class TVstation {

	private int mStationId;
	private String mStationName = "";
	
	public TVstation(int id, String stationName) {
		mStationId = id;
		mStationName = stationName;
	}
	public int getStationId() {
		return mStationId;
	}

	public void setStationId(int id) {
		this.mStationId = id;
	}

	public String getStationName() {
		return mStationName;
	}

	public void setStationName(String stationName) {
		this.mStationName = stationName;
	}

}
