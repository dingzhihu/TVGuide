package com.howfun.android.tv;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String prefStr = getResources().getString(R.string.tab_pref);
		String guideStr = getResources().getString(R.string.tab_guide);
		Drawable prefDrawable = getResources().getDrawable(R.drawable.pref);
		Drawable guideDrawable = getResources().getDrawable(R.drawable.guide);
		final TabHost tabHost = getTabHost();

		tabHost
				.addTab(tabHost.newTabSpec("tab1").setIndicator(guideStr,
						guideDrawable).setContent(
						new Intent(this, AreaActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(prefStr,
				prefDrawable).setContent(
				new Intent(this, PreferenceActivity.class)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.about, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.about:
			about();
			break;
		}
		return true;
	}

	private void about() {
		new AlertDialog.Builder(this).setIcon(R.drawable.icon).setTitle(
				R.string.app_name).setMessage(R.string.about)
				.setPositiveButton(android.R.string.ok, null).show();
	}


}
