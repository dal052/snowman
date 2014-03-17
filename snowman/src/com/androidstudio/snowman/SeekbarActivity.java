package com.androidstudio.snowman;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SeekbarActivity extends Activity {
	public static String NOTIINT = "com.androidstudio.snowman.SeekbarActivity.NOTIINT";

	private SeekBar volumeControl = null;
	private long notiRate = 20;
	private int notiInt = 20;

	ListView list;

	String[] listContent = {"CSE 100", "CSE 110", "History", "Math", "Chinese", "Interviews", "Vocabulary"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_seekbar);

		this.setFinishOnTouchOutside(false); // clicking out side doesnt close activity
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);

		volumeControl = (SeekBar) findViewById(R.id.volume_bar); // seek bar
		list = (ListView) findViewById(R.id.listView1); // list view

		// the seekbar notification
		volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//				stopService(new Intent(SeekbarActivity.this, NotiService.class));
			}

			public void onStopTrackingTouch(SeekBar seekBar) {

				Intent intent = new Intent(SeekbarActivity.this, NotiService.class);

				if(progressChanged == 0){	
					stopService(intent);
					Toast.makeText(SeekbarActivity.this, " Notification Off", Toast.LENGTH_SHORT).show();
				}
				else{
					notiInt = progressChanged;

					intent.putExtra(NOTIINT, notiInt);
					Log.w("test", "before service notiInt: " + notiInt);
					startService(intent);

					Toast.makeText(SeekbarActivity.this, progressChanged + " Study Buddies per Hour", 
							Toast.LENGTH_SHORT).show();
				}
			}
		});



		// expand list view for group select
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
				R.layout.custom_list_item_multiple_choice, prepareList()); 

		list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // allow our list to have multiple choice boxes
		list.setAdapter(adapter); // set our adapter to the list.
		

		String selected = "";
		int cnt = list.getCount();
		SparseBooleanArray listbool = list.getCheckedItemPositions();

		for(int i = 0; i < cnt; i++){
			if(listbool.get(i)){
				selected += list.getItemAtPosition(i).toString() + "\n";
			}
			Toast.makeText(SeekbarActivity.this,selected,Toast.LENGTH_LONG).show();

		}



	}

	public void seekbarButtons(View view){

		switch(view.getId()){

		case R.id.settingOk:

		case R.id.settingCancle:
			break;
		}
		finish();
	}

	
	// populate the list  get the list of group name from our database here
	public String[] prepareList(){

		// get the list of groups here

		return listContent;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seekbar, menu);
		return true;
	}

}