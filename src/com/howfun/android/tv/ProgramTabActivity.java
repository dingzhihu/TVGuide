package com.howfun.android.tv;


import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class ProgramTabActivity extends ActivityGroup {

	public static TabHost tab_host;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setTitle(R.string.program);
        setContentView(R.layout.bottomtab);
        

        tab_host = (TabHost) findViewById(R.id.edit_item_tab_host);
        tab_host.setup(this.getLocalActivityManager());
        
        int channleId = getIntent().getIntExtra(Utils.CHANNEL_ID, -1000);
        Intent intent1 = new Intent(this, ProgramActivity.class);
        intent1.putExtra(Utils.CHANNEL_ID, channleId);
        intent1.putExtra(Utils.PROGRAM_DATE, Utils.getDate(0));
        Intent intent2 = new Intent(this, ProgramActivity.class);
        intent2.putExtra(Utils.CHANNEL_ID, channleId);
        intent2.putExtra(Utils.PROGRAM_DATE, Utils.getDate(1));
        Intent intent3 = new Intent(this, ProgramActivity.class);
        intent3.putExtra(Utils.CHANNEL_ID, channleId);
        intent3.putExtra(Utils.PROGRAM_DATE, Utils.getDate(2));
        

        TabSpec ts1 = tab_host.newTabSpec("tab1"); 
        ts1.setIndicator(getResources().getString(R.string.today)); 
        ts1.setContent(intent1); 
        tab_host.addTab(ts1);

        TabSpec ts2 = tab_host.newTabSpec("tab2"); 
        ts2.setIndicator(getResources().getString(R.string.tomorrow)); 
        ts2.setContent(intent2); 
        tab_host.addTab(ts2);

        TabSpec ts3 = tab_host.newTabSpec("tab3"); 
        ts3.setIndicator(getResources().getString(R.string.day_after_tomorrow)); 
        ts3.setContent(intent3); 
        tab_host.addTab(ts3);
        
        tab_host.setCurrentTab(0);
	}

}
