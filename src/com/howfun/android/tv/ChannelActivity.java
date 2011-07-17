package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.webservice.WebserviceAdapter;
import com.howfun.android.webservice.WebserviceI;

public class ChannelActivity extends Activity {

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Utils.MSG_NETWORK_ERROR:
				setProgressBarIndeterminateVisibility(false);
				mTextView.setVisibility(View.VISIBLE);
				// TODO hint of network error
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
	List<TVchannel> mChannelList = new ArrayList<TVchannel>();
	private ListView mListView = null;
	private TextView mTextView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setTitle(R.string.channel);
		this.setContentView(R.layout.channel);
		mListView = (ListView) findViewById(R.id.channel_list);
		mTextView = (TextView) findViewById(R.id.channel_text);

		setProgressBarIndeterminateVisibility(true);
		new Thread() {
			public void run() {
				int stationId = getIntent()
						.getIntExtra(Utils.STATION_ID, -1000);
				mChannelList = mWebservice.getChannelList(stationId);
				if (mChannelList != null) {
					if (mChannelList.size() == 0) {
						mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
					} else {
						mHandler.sendEmptyMessage(Utils.MSG_GUIDE_UPDATED);
					}
				} else {
					mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
				}
			}
		}.start();

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				TVchannel channel = (TVchannel) arg0.getAdapter().getItem(arg2);
				//add pref channel into db
				if(channel != null){
					DbAdapter dbAdapter = new DbAdapter(ChannelActivity.this);
					dbAdapter.open();
					dbAdapter.addChannel(channel);
					dbAdapter.close();
					
					Intent intent = new Intent(ChannelActivity.this,
							ProgramTabActivity.class);
					intent.putExtra(Utils.CHANNEL_ID, channel.getChannelId());
					startActivity(intent);
				}
			}
		});
	}

	private void update() {
		Adapter adapter = new Adapter(this,
				android.R.layout.simple_list_item_1, mChannelList);
		mListView.setAdapter(adapter);
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
