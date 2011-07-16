package com.howfun.android.tv.entity;

public class Area {

	private int mAreaId;
	private String mAreaName = "";
	private String mAreaZone = "";

	public Area(int id, String areaName, String areaZone) {
		mAreaId = id;
		mAreaName = areaName;
		mAreaZone = areaZone;
	}

	public Area(int id, String areaName) {
		mAreaId = id;
		mAreaName = areaName;
	}

	public int getAreaId() {
		return mAreaId;
	}

	public void setAreaId(int areaId) {
		this.mAreaId = areaId;
	}

	public String getAreaName() {
		return mAreaName;
	}

	public void setAreaName(String areaName) {
		this.mAreaName = areaName;
	}

	public String getAreaZone() {
		return mAreaZone;
	}

	public void setAreaZone(String areaZone) {
		this.mAreaZone = areaZone;
	}

	public String toString(){
		return "areaId:"+String.valueOf(mAreaId)+",areaName:"+mAreaName+",areaZone:"+mAreaZone;
	}
}
