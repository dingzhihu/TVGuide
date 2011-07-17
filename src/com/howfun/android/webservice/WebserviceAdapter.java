package com.howfun.android.webservice;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.howfun.android.tv.Utils;
import com.howfun.android.tv.entity.Area;
import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;
import com.howfun.android.tv.entity.TVstation;

public class WebserviceAdapter implements WebserviceI {
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	// WebServiceµÿ÷∑
	private static String URL = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx";

	private static final String METHOD_AREA = "getAreaString";
	private static final String ACTION_AREA = "http://WebXml.com.cn/getAreaString";
	private static final String PROPERTY_AREA = "getAreaStringResult";

	private static final String METHOD_STATION = "getTVstationString";
	private static final String ACTION_STATION = "http://WebXml.com.cn/getTVstationString";
	private static final String PROPERTY_STATION = "getTVstationStringResult";

	private static final String METHOD_CHANNEL = "getTVchannelString";
	private static final String ACTION_CHANNEL = "http://WebXml.com.cn/getTVchannelString";
	private static final String PROPERTY_CHANNEL = "getTVchannelStringResult";

	private static final String METHOD_PROGRAM = "getTVprogramString";
	private static final String ACTION_PROGRAM = "http://WebXml.com.cn/getTVprogramString";
	private static final String PROPERTY_PROGRAM = "getTVprogramStringResult";

	@Override
	public List<Area> getAreaList() {
		List<Area> areaList = new ArrayList<Area>();
		SoapObject detail = null;
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_AREA);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_AREA, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result.getProperty(PROPERTY_AREA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (detail != null) {
			for (int i = 0; i < detail.getPropertyCount(); i++) {
				Area area = parseArea(detail.getProperty(i).toString());
				if (area != null) {
					areaList.add(area);
				}
			}
		}
		return areaList;
	}

	@Override
	public List<TVchannel> getChannelList(int stationId) {
		SoapObject detail = null;
		List<TVchannel> channelList = new ArrayList<TVchannel>();
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_CHANNEL);

			rpc.addProperty("theTVstationID", stationId);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_CHANNEL, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result.getProperty(PROPERTY_CHANNEL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (detail != null) {
			for (int i = 0; i < detail.getPropertyCount(); i++) {
				TVchannel channel = parseChannel(detail.getProperty(i)
						.toString());
				if (channel != null) {
					channelList.add(channel);
				}
			}
		}
		return channelList;
	}

	@Override
	public List<TVprogram> getProgramList(int channelId, String date) {
		SoapObject detail = null;
		List<TVprogram> programList = new ArrayList<TVprogram>();
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_PROGRAM);

			rpc.addProperty("theTVchannelID", channelId);
			rpc.addProperty("theDate", date);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_PROGRAM, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result.getProperty(PROPERTY_PROGRAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(detail != null){
			for(int i=0;i<detail.getPropertyCount();i++){
				TVprogram program = parseProgram(detail.getProperty(i).toString());
				if(program != null){
					programList.add(program);
				}
			}
		}
		return programList;
	}

	@Override
	public List<TVstation> getStationList(int areaId) {
		SoapObject detail = null;
		List<TVstation> stationList = new ArrayList<TVstation>();
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_STATION);

			rpc.addProperty("theAreaID", areaId);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_STATION, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result.getProperty(PROPERTY_STATION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (detail != null) {
			for (int i = 0; i < detail.getPropertyCount(); i++) {
				TVstation station = parseStation(detail.getProperty(i)
						.toString());
				if (station != null) {
					stationList.add(station);
				}
			}
		}
		return stationList;
	}

	private Area parseArea(String areaStr) {
		Area area = null;
		if (areaStr != null && !"".equals(areaStr)) {
			String[] info = areaStr.split("@");
			int id = Integer.valueOf(info[0]);
			String areaName = info[1];
			String areaZone = info[2];
			area = new Area(id, areaName, areaZone);
		}
		return area;
	}

	private TVstation parseStation(String stationStr) {
		TVstation station = null;
		if (stationStr != null && !"".equals(stationStr)) {
			String[] info = stationStr.split("@");
			int id = Integer.parseInt(info[0]);
			String stationName = info[1];
			station = new TVstation(id, stationName);
		}
		return station;

	}

	private TVchannel parseChannel(String channelStr) {
		TVchannel channel = null;
		if (channelStr != null && !"".equals(channelStr)) {
			String[] info = channelStr.split("@");
			int id = Integer.parseInt(info[0]);
			String channelName = info[1];
			channel = new TVchannel(id, channelName);
		}
		return channel;
	}

	private TVprogram parseProgram(String programStr){
		TVprogram program = null;
		if(programStr != null && !"".equals(programStr)){
			String[]info = programStr.split("@@@");
			String time = info[0];
			String playTime = getPlayTime(time);
			String meridiem = getMeridiem(time);
			String programInfo = info[1];
			String stationInfo = info[2];
			program = new TVprogram(playTime, meridiem, programInfo, stationInfo);
		}
		return program;
	}
	
	private String getMeridiem(String time){
		if(time != null){
			if(time.contains(Utils.AM)){
				return Utils.AM;
			}else if(time.contains(Utils.PM)){
				return Utils.PM;
			}
		}
		return Utils.NAN;
	}
	
	private String getPlayTime(String time){
		if(time != null){
			int index = time.indexOf("(");
			return time.substring(0, index);
		}
		return Utils.NAN;
	}
}
