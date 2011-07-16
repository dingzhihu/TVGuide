package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import com.howfun.android.tv.entity.TVchannel;

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

public class ChannelActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.channel);
		ListView list = (ListView) findViewById(R.id.channel_list);

		WebserviceI webservice = new WebserviceAdapter();
		List<TVchannel> channelList = new ArrayList<TVchannel>();
		int stationId = getIntent().getIntExtra(Utils.STATION_ID, -1000);
		// TODO new thread
		channelList = webservice.getChannelList(stationId);
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, channelList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TVchannel channel = (TVchannel) arg0.getAdapter().getItem(arg2);
				Intent intent = new Intent(ChannelActivity.this,
						ProgramActivity.class);
				intent.putExtra(Utils.CHANNEL_ID, channel.getChannelId());
				startActivity(intent);

			}
		});
	}

	private static class Adapter extends ArrayAdapter<TVchannel> {

		private Context mCtx;
		private int mRes;
		private LayoutInflater mInflater;

		public Adapter(Context context, int resource, List<TVchannel> objects) {
			super(context, resource, objects);
			mCtx = context;
			mRes = resource;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			TVchannel item = getItem(position);

			if (convertView == null) {
				convertView = mInflater.inflate(mRes, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(android.R.id.text1);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(item.getChannelName());

			return convertView;
		}

		private static class ViewHolder {
			TextView text;
		}

	}
}
