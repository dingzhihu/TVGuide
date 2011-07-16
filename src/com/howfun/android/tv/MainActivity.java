package com.howfun.android.tv;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TabHost tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("list").setContent(new Intent(this,PreferenceActivity.class)));

        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("photo list").setContent(new Intent(this, AreaActivity.class))
                );
	}

}
