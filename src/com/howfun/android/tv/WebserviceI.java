package com.howfun.android.tv;

import java.util.List;

import com.howfun.android.tv.entity.Area;
import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;
import com.howfun.android.tv.entity.TVstation;

public interface WebserviceI {

//	public List<Area> getAreaList();
//	public List<TVstation> getStationList(int areaId);
//	public List<TVchannel> getChannelList(int stationId);
//	public List<TVprogram> getProgramList(int channelId,String date);
	
	public String getAreaList();
	public String getStationList(int areaId);
	public String getChannelList(int stationId);
	public String getProgramList(int channelId,String date);
}
