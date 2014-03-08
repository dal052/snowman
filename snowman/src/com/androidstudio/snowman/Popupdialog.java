package com.androidstudio.snowman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class Popupdialog extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set icon for popup
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_popupdialog);
		// set icon for popup
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);

		// initialize my activity to the view to use in the listener
		TextView text = (TextView) findViewById(R.id.text);
	
		
		// start the touch listener 
		text.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){		
						// intent to switch between to next activity
					Intent resInt = new Intent(Popupdialog.this, Popupdialog2.class);	
					
					// stop service
					stopService(new Intent(Popupdialog.this, NotiService.class));

					finish();	// finish the first activity while flipping.	
					startActivity(resInt); // start the next activity.
					
					return true; // return true if activity was touched
				}
				return false;	// return false if activity is not touched.
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popupdialog, menu);
		return true;
	}


}
