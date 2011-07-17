package com.howfun.android.tv;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.howfun.android.tv.entity.Area;
import com.howfun.android.webservice.WebserviceAdapter;
import com.howfun.android.webservice.WebserviceI;

public class SplashActivity extends Activity {
	private DbAdapter mDbAdapter = new DbAdapter(this);
	private WebserviceI mWebservice = new WebserviceAdapter();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case Utils.MSG_NETWORK_ERROR:
				//TODO a hint of network error
				mTextView.setVisibility(View.GONE);
				break;

			default:
				break;
			}
		}
	};

	private TextView mTextView = null;
	private TextView mTextViewEmpty = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		mTextView = (TextView) findViewById(R.id.splash_text);
		mTextViewEmpty = (TextView) findViewById(R.id.splash_text2);
		mDbAdapter.open();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				//TODO update?
				Cursor cur = mDbAdapter.getAllAreas();
				startManagingCursor(cur);
				if (cur == null || cur.getCount() == 0) {
					List<Area> areaList = mWebservice.getAreaList();
					if (areaList != null) {
						if(areaList.size() == 0){
							mHandler.sendEmptyMessage(Utils.MSG_NETWORK_ERROR);
							return;
						}
						Iterator<Area> it = areaList.iterator();
						while (it.hasNext()) {
							Area area = it.next();
							mDbAdapter.addArea(area);
						}
					}
				}
				Intent mainIntent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(mainIntent);
				finish();

			}
		}, 0);
	}

	public void onDestroy() {
		super.onDestroy();
		mDbAdapter.close();
	}
}