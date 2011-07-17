package com.howfun.android.tv;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.howfun.android.tv.entity.TVchannel;

public class PreferenceActivity extends Activity {
	
	private ListView mListView = null;
	Adapter mAdapter = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference);
		
		mListView = (ListView) findViewById(R.id.pref_channel_list);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TVchannel channel = (TVchannel) arg0.getAdapter().getItem(arg2);
				if(channel != null){
					Intent intent = new Intent(PreferenceActivity.this,
							ProgramTabActivity.class);
					intent.putExtra(Utils.CHANNEL_ID, channel.getChannelId());
					startActivity(intent);
				}
			}
		});
		
	}
	
	public void onResume(){
		super.onResume();
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		List<TVchannel> prefChannelList = dbAdapter.getPrefChannelList();
		dbAdapter.close();
		if(prefChannelList !=null && prefChannelList.size() > 0){
			mListView.setVisibility(View.VISIBLE);
			mAdapter = new Adapter(this,
					android.R.layout.simple_list_item_1, prefChannelList);
			mListView.setAdapter(mAdapter);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.clear, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.clear:
			clearChannelPrefs();
			break;
		}
		return true;
	}

	private void clearChannelPrefs() {
		DbAdapter dbAdapter = new DbAdapter(this);
		dbAdapter.open();
		dbAdapter.delPrefChannels();
		dbAdapter.close();
		
		if(mAdapter != null){
			mListView.setVisibility(View.GONE);
		}
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
				holder.text.setTextColor(Color.BLACK);

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
