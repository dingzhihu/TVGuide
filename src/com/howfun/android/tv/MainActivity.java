package com.howfun.android.tv;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {


	private Button okButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		okButton = (Button) findViewById(R.id.btn);  

		  okButton.setOnClickListener(new Button.OnClickListener() {  
		      public void onClick(View v) {  
//		         showWeather();  
		    	  Utils.showMessageDlg(MainActivity.this, new WebserviceAdapter().getProgramList(23, "").toString());
		      }  
		  });  
	}


	
}