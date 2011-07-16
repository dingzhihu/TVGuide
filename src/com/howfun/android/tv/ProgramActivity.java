package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProgramActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.program);

		ListView list = (ListView) findViewById(R.id.program_list);

		WebserviceI webservice = new WebserviceAdapter();
		List<TVprogram> programList = new ArrayList<TVprogram>();
		int channelId = getIntent().getIntExtra(Utils.CHANNEL_ID, -1000);
		System.out.println("channel id:"+channelId);
		// TODO new thread
		programList = webservice.getProgramList(channelId, "");
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, programList);
		list.setAdapter(adapter);
	}

	private static class Adapter extends ArrayAdapter<TVprogram> {

		private Context mCtx;
		private int mRes;
		private LayoutInflater mInflater;

		public Adapter(Context context, int resource, List<TVprogram> objects) {
			super(context, resource, objects);
			mCtx = context;
			mRes = resource;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			TVprogram item = getItem(position);

			if (convertView == null) {
				convertView = mInflater.inflate(mRes, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) convertView
						.findViewById(android.R.id.text1);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(item.getProgramInfo());

			return convertView;
		}

		private static class ViewHolder {
			TextView text;
		}

	}
}
