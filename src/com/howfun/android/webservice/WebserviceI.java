package com.howfun.android.webservice;

import java.util.List;

import com.howfun.android.tv.entity.Area;
import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;
import com.howfun.android.tv.entity.TVstation;

public interface WebserviceI {

	public List<Area> getAreaList();

	public List<TVstation> getStationList(int areaId);

	public List<TVchannel> getChannelList(int stationId);

	public List<TVprogram> getProgramList(int channelId, String date);
}
