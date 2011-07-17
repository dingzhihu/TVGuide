package com.howfun.android.tv;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.howfun.android.tv.entity.Area;

public class AreaActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.area);
		ListView list = (ListView) findViewById(R.id.area_list);
		list.setCacheColorHint(0);
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		List<Area> areaList = dbAdapter.getAreaList();
		dbAdapter.close();
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, areaList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Area area = (Area) arg0.getAdapter().getItem(arg2);
				Intent intent = new Intent(AreaActivity.this,
						StationActivity.class);
				intent.putExtra(Utils.AREA_ID, area.getAreaId());
				startActivity(intent);

			}
		});

	}

	protected void onListItemClick(ListView l, View v, int position, long id) {

	}

	private static class Adapter extends ArrayAdapter<Area> {

		private Context mCtx;
		private int mRes;
		private LayoutInflater mInflater;

		public Adapter(Context context, int resource, List<Area> objects) {
			super(context, resource, resource, objects);
			mCtx = context;
			mRes = resource;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			Area item = getItem(position);

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

			holder.text.setText(item.getAreaName());

			return convertView;
		}

		private static class ViewHolder {
			TextView text;
		}

	}
}
