package com.androidstudio.snowman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Frequencies extends Activity {

	private TextView rate; // text view that shows the number of noti per hour
	private long notiRate = 60; // long value is for calculating milisecond time for service
	private int showRate = 60;	// showRate is for showing the user the int value

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frequencies);
		
		// set the 
		rate = (TextView) findViewById(R.id.rate);
		rate.setText(Long.toString(showRate));

	}

	public void changeFreq(View view){
		
		
		// stop the notiservice when button is clicked
		stopService(new Intent(Frequencies.this, NotiService.class));

		// on plus increment the frequency by 10 cards
		switch(view.getId()){
		case R.id.plus:
			showRate = showRate + 10;
			notiRate = notiRate + 10;
//			Toast toast = Toast.makeText(getApplicationContext(), Long.toString(calculateRate()), Toast.LENGTH_SHORT);
//			toast.show();

			rate.setText(Integer.toString(showRate)); // update the textview 	
			startService(new Intent(Frequencies.this, NotiService.class)); // restart the service
			break;
			
		// on minus decrement the counters 
		case R.id.minus:
			showRate = showRate - 10;
			notiRate = notiRate - 10;
			
//		    toast = Toast.makeText(getApplicationContext(), Long.toString(calculateRate()), Toast.LENGTH_SHORT);
//			toast.show();

			rate.setText(Integer.toString(showRate));
			startService(new Intent(Frequencies.this, NotiService.class));

			break;
		case R.id.finish:
			finish();			
		}
	}

	// calculate the frequencies to milisecond.
	public long calculateRate(){
		long rate = 0;
		rate = (1000 * 3600)/notiRate; 	
		
		return rate;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.frequencies, menu);
		return true;
	}
}
