package com.androidstudio.snowman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class Popupdialog2 extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);

		setContentView(R.layout.activity_popupdialog2);
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);

		TextView text = (TextView) findViewById(R.id.text2); // initialize view
		
		
		overridePendingTransition(R.anim.popupfront, R.anim.popupback);	// do the flip animation.

		// start the touch listener 
		text.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
					
					// TODO Auto-generated method stub
					// on click touch down do the ...
				if(event.getAction() == MotionEvent.ACTION_DOWN){
					
					// restart service at the end of finishing the popup

					finish();	// on touch exit the popup
					

					return true;
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.popupdialog2, menu);
		return true;
	}
}
