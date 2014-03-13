package com.androidstudio.snowman;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;


public class Popupdialog extends Activity {
	

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		// set icon for popup
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  enabele 1

		requestWindowFeature(Window.FEATURE_LEFT_ICON);

		setContentView(R.layout.activity_popupdialog);
		
//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutombar); enable 2 for custom action bar

		
		
		/*
		int colors[] = {0x00ACED, 0x27DADF};
//		ActionBar actionbar = getActionBar();
		View layout = findViewById(R.id.mainlayout);
		GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
		gradient.setCornerRadius(0f);
//		actionbar.setBackgroundDrawable(gradient);
		layout.setBackgroundDrawable(gradient);
		
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.cutombar);
	    View title = getWindow().findViewById(R.id.mainlayout);
	    title.setBackgroundDrawable(gradient); */
		
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);
		
		

		// initialize my activity to the view to use in the listener
		TextView text = (TextView) findViewById(R.id.text);
		// stop service
		stopService(new Intent(Popupdialog.this, NotiService.class));
	
		// start the touch listener 
		text.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN){		
						// intent to switch between to next activity
					Intent resInt = new Intent(Popupdialog.this, Popupdialog2.class);	
					
					

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
