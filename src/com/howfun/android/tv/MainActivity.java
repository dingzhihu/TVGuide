package com.howfun.android.tv;

import java.io.UnsupportedEncodingException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String NAMESPACE = "http://WebXml.com.cn/";
	// WebService地址
//	private static String URL = "http://www.webxml.com.cn/webservices/weatherwebservice.asmx";
	private static String URL = "http://webservice.webxml.com.cn/webservices/ChinaTVprogramWebService.asmx";
	private static final String METHOD_NAME = "getTVStationString";
//	private static String SOAP_ACTION = "http://WebXml.com.cn/getWeatherbyCityName";
	
//	private static final String METHOD_NAME = "getAreaDataSet";
	private static String SOAP_ACTION = "http://WebXml.com.cn/getTVstationString";

	private String weatherToday;

	private Button okButton;
	private SoapObject detail;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		okButton = (Button) findViewById(R.id.btn);  

		  okButton.setOnClickListener(new Button.OnClickListener() {  
		      public void onClick(View v) {  
//		         showWeather();  
		    	  Utils.showMessageDlg(MainActivity.this, new WebserviceAdapter().getAreaList().toString());
		      }  
		  });  
	}

	private void showWeather() {
		String city = "武汉";
		getWeather(city);
	}

	@SuppressWarnings("deprecation")
	public void getWeather(String cityName) {
		try {
			System.out.println("rpc------");
			SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
			System.out.println("rpc" + rpc);
			System.out.println("cityName is " + cityName);
//			rpc.addProperty("theCityName", cityName);
			rpc.addProperty("theAreaID", 12);


			HttpTransportSE ht = new HttpTransportSE(URL);
//			AndroidHttpTransport ht = new AndroidHttpTransport(URL);
			ht.debug = true;

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);

			envelope.bodyOut = rpc;
//			envelope.dotNet = true;
			envelope.setOutputSoapObject(rpc);

			ht.call(SOAP_ACTION, envelope);

			SoapObject result = (SoapObject) envelope.bodyIn;
			detail = (SoapObject) result
					.getProperty("getTVstationStringResult");
//			
//			detail = (SoapObject) result
//			.getProperty("getAreaDataSetResult");

			System.out.println("result" + result);
			System.out.println("detail" + detail);
//			Toast.makeText(this, detail.toString(),
//					Toast.LENGTH_LONG).show();
			System.out.println(detail.toString());
			Utils.showMessageDlg(this, detail.toString());
//			parseWeather(detail);

			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseWeather(SoapObject detail)
			throws UnsupportedEncodingException {
		String date = detail.getProperty(6).toString();
		weatherToday = "今天：" + date.split(" ")[0];
		weatherToday = weatherToday + "\n天气：" + date.split(" ")[1];
		weatherToday = weatherToday + "\n气温："
				+ detail.getProperty(5).toString();
		weatherToday = weatherToday + "\n风力："
				+ detail.getProperty(7).toString() + "\n";
		System.out.println("weatherToday is " + weatherToday);
		Toast.makeText(this, weatherToday, Toast.LENGTH_LONG).show();

	}
	
	
}