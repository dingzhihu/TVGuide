package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.howfun.android.tv.entity.TVstation;

public class StationActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.station);
		ListView list = (ListView) findViewById(R.id.station_list);
		
		WebserviceI webservice = new WebserviceAdapter();
		List<TVstation> stationList = new ArrayList<TVstation>();
		int areaId = getIntent().getIntExtra(Utils.AREA_ID, -1000);
		//TODO new thread
		stationList = webservice.getStationList(areaId);
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, stationList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

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
