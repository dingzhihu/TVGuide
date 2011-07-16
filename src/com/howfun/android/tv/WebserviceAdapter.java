package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import com.howfun.android.tv.entity.Area;
import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;
import com.howfun.android.tv.entity.TVstation;

public class WebserviceAdapter implements WebserviceI {
	private static final String NAMESPACE = "http://WebXml.com.cn/";
	// WebService��ַ
	private static String URL = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx";

	private static final String METHOD_AREA_STRING = "getAreaString";
	private static final String ACTION_AREA_STRING = "http://WebXml.com.cn/getAreaString";
	private static final String PROPERTY_AREA_STRING = "getAreaStringResult";

	private static final String METHOD_STATION_STRING = "getTVstationString";
	private static final String ACTION_STATION_STRING = "http://WebXml.com.cn/getTVstationString";
	private static final String PROPERTY_STATION_STRING = "getTVstationStringResult";

	private static final String METHOD_CHANNEL_STRING = "getTVchannelString";
	private static final String ACTION_CHANNEL_STRING = "http://WebXml.com.cn/getTVchannelString";
	private static final String PROPERTY_CHANNEL_STRING = "getTVchannelStringResult";

	private static final String METHOD_PROGRAM_STRING = "getTVprogramString";
	private static final String ACTION_PROGRAM_STRING = "http://WebXml.com.cn/getTVprogramString";
	private static final String PROPERTY_PROGRAM_STRING = "getTVprogramStringResult";

	@Override
	public List<Area> getAreaList() {
		List<Area> areaList = new ArrayList<Area>();
		SoapObject detail = null;
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_AREA_STRING);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_AREA_STRING, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result
					.getProperty(PROPERTY_AREA_STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(detail != null){
			for(int i=0;i<detail.getPropertyCount();i++){
				Area area = parseArea(detail.getProperty(i).toString());
				if(area != null){
					areaList.add(area);
				}
			}
		}
		return areaList;
	}

	@Override
	public String getChannelList(int stationId) {
		SoapObject detail = null;
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_CHANNEL_STRING);

			rpc.addProperty("theTVstationID", stationId);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_CHANNEL_STRING, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result
					.getProperty(PROPERTY_CHANNEL_STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail.toString();
	}

	@Override
	public String getProgramList(int channelId, String date) {
		SoapObject detail = null;
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_PROGRAM_STRING);

			rpc.addProperty("theTVchannelID",channelId);
			rpc.addProperty("theDate", date);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_PROGRAM_STRING, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result
					.getProperty(PROPERTY_PROGRAM_STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail.toString();
	}

	@Override
	public String getStationList(int areaId) {
		SoapObject detail = null;
		try {
			HttpTransportSE ht = new HttpTransportSE(URL);
			ht.debug = true;
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_STATION_STRING);

			rpc.addProperty("theAreaID", areaId);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(ACTION_STATION_STRING, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result
					.getProperty(PROPERTY_STATION_STRING);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return detail.toString();
	}
	
	private Area parseArea(String areaStr){
		Area area = null;
		if(areaStr != null && !"".equals(areaStr)){
			System.out.println("areaStr:"+areaStr);
			String[]info = areaStr.split("@");
			int id = Integer.valueOf(info[0]);
			String areaName = info[1];
			String areaZone = info[2];
			area = new Area(id, areaName, areaZone);
		}
		return area;
	}

}
