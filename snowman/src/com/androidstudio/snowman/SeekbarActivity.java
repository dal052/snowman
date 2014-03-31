package com.androidstudio.snowman;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	public static String NOTIPREFS = "com.androidstudio.snowman.SeekbarActivity.NOTIPREFS";
	
	private SeekBar barControl = null;
	private SharedPreferences Prefs;
	private SharedPreferences.Editor PrefEditor;
	private ListView list;
	
	public int progress;
	
	String[] listContent = {"CSE 100", "CSE 110", "History", "Math", "Chinese", "Interviews", "Vocabulary"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_seekbar);

//		this.setFinishOnTouchOutside(false); // clicking out side doesnt close activity
		getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.sbbox);

		list = (ListView) findViewById(R.id.listView1); // list view
		

		Prefs = getSharedPreferences(NOTIPREFS, Context.MODE_PRIVATE);
		PrefEditor = Prefs.edit();
		
		int number = Prefs.getInt(NOTIINT, 20);
		barControl = (SeekBar) findViewById(R.id.volume_bar); // seek bar
		barControl.setProgress(number);

		// the seekbar notification
		barControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			int progressChanged = 0;

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				progressChanged = progress;
			}

			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//				stopService(new Intent(SeekbarActivity.this, NotiService.class));
			}

			public void onStopTrackingTouch(SeekBar seekBar) {

//				Intent intent = new Intent(SeekbarActivity.this, NotiService.class);

				if(progressChanged == 0){	
//					stopService(intent);
					Toast.makeText(SeekbarActivity.this, " Notification Off", Toast.LENGTH_SHORT).show();
				}
				else{
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
//			Toast.makeText(SeekbarActivity.this,selected,Toast.LENGTH_LONG).show();

		}
	}
	
	@Override
	public void onStop(){
		super.onStop();

	}

	public void seekbarButtons(View view){
	
		Intent intent = new Intent(SeekbarActivity.this, NotiService.class);
		progress = barControl.getProgress();
		
		switch(view.getId()){
		case R.id.settingOk:
			
			if(barControl.getProgress() == 0)
				stopService(intent);
			else{
				stopService(intent);
				intent.putExtra(NOTIINT, progress);
				startService(intent);
			}
			PrefEditor.putInt(NOTIINT, progress );
			PrefEditor.commit();
				
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