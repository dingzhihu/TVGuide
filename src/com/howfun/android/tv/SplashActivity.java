package com.howfun.android.tv;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import com.howfun.android.tv.entity.Area;

public class SplashActivity extends Activity {

	private DbAdapter mDbAdapter = new DbAdapter(this);
	private WebserviceI mWebservice = new WebserviceAdapter();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
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