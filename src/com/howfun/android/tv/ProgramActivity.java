package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.howfun.android.tv.entity.TVchannel;
import com.howfun.android.tv.entity.TVprogram;
import com.howfun.android.webservice.WebserviceAdapter;
import com.howfun.android.webservice.WebserviceI;

public class ProgramActivity extends Activity {

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
	List<TVprogram> mProgramList = new ArrayList<TVprogram>();
	private ListView mListView = null;
	private TextView mTextView = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		// setTitle(R.string.program);
		setContentView(R.layout.program);

		mListView = (ListView) findViewById(R.id.program_list);
		mTextView = (TextView) findViewById(R.id.program_text);

		mListView.setSelector(android.R.color.transparent);
		// mListView.setCacheColorHint(0);
		setProgressBarIndeterminateVisibility(true);
		new Thread() {
			public void run() {
				int channelId = getIntent()
						.getIntExtra(Utils.CHANNEL_ID, -1000);
				String date = getIntent().getStringExtra(Utils.PROGRAM_DATE);
				mProgramList = mWebservice.getProgramList(channelId, date);
				if (mProgramList != null) {
					if (mProgramList.size() == 0) {
						mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
					} else {
						mHandler.sendEmptyMessage(Utils.MSG_GUIDE_UPDATED);
					}
				} else {
					mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
				}
			}
		}.start();

	}

	private void update() {
		Adapter adapter = new Adapter(this, R.layout.program_item, mProgramList);
		mListView.setAdapter(adapter);
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
				holder.playTime = (TextView) convertView
						.findViewById(R.id.program_playtime);
				holder.programInfo = (TextView) convertView
						.findViewById(R.id.program_info);
				holder.playTime.setBackgroundColor(0xff808080);
				holder.programInfo.setBackgroundColor(0xff404040);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.playTime.setText(item.getPlayTime());
			holder.programInfo.setText(item.getProgramInfo());

			return convertView;
		}

		private static class ViewHolder {
			TextView playTime;
			TextView programInfo;
		}

	}
}
