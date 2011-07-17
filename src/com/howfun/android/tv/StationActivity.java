package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.howfun.android.tv.entity.TVstation;
import com.howfun.android.webservice.WebserviceAdapter;
import com.howfun.android.webservice.WebserviceI;

public class StationActivity extends Activity {
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case Utils.MSG_NETWORK_ERROR:
				setProgressBarIndeterminateVisibility(false);
				mTextView.setVisibility(View.VISIBLE);
				//TODO hint of network error
				break;
			case Utils.MSG_GUIDE_UPDATED:
				setProgressBarIndeterminateVisibility(false);
				mListView.setVisibility(View.VISIBLE);
				update();
				break;
			default:
				break;
			}
		}
	};
	
	private WebserviceI mWebservice = new WebserviceAdapter();
	List<TVstation> mStationList = new ArrayList<TVstation>();
	private ListView mListView = null;
	private TextView mTextView = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setTitle(R.string.station);
		setContentView(R.layout.station);
		mListView = (ListView) findViewById(R.id.station_list);
		mListView.setCacheColorHint(0);
		mTextView = (TextView) findViewById(R.id.station_text);
		
		setProgressBarIndeterminateVisibility(true);
		new Thread(){
			public void run(){
				int areaId = getIntent().getIntExtra(Utils.AREA_ID, -1000);
				mStationList = mWebservice.getStationList(areaId);
				if(mStationList != null){
					if(mStationList.size() ==0){
						mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
					}else{
						mHandler.sendEmptyMessage(Utils.MSG_GUIDE_UPDATED);
					}
				}else{
					mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
				}
			}
		}.start();
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TVstation station = (TVstation) arg0.getAdapter().getItem(arg2);
				Intent intent = new Intent(StationActivity.this,
						ChannelActivity.class);
				intent.putExtra(Utils.STATION_ID, station.getStationId());
				startActivity(intent);

			}
		});
	}
	
	private void update(){
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, mStationList);
		mListView.setAdapter(adapter);
	}

	
	private static class Adapter extends ArrayAdapter<TVstation> {

		private Context mCtx;
		private int mRes;
		private LayoutInflater mInflater;

		public Adapter(Context context, int resource, List<TVstation> objects) {
			super(context, resource, objects);
			mCtx = context;
			mRes = resource;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			TVstation item = getItem(position);

			if (convertView == null) {
				convertView = mInflater.inflate(mRes, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(android.R.id.text1);
				holder.text.setTextColor(Color.BLACK);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(item.getStationName());

			return convertView;
		}

		private static class ViewHolder {
			TextView text;
		}

	}
}
