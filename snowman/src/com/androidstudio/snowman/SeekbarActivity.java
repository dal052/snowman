package com.androidstudio.snowman;



import android.app.Activity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SeekbarActivity extends Activity {
	 
    private SeekBar volumeControl = null;
    
    ListView list;
    
    String[] listContent = {"adf", "adfafsd"};
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		requestWindowFeature(Window.FEATURE_LEFT_ICON);

        setContentView(R.layout.activity_seekbar);
        
//        this.setFinishOnTouchOutside(false); // clicking out side doesnt close activity

        
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
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SeekbarActivity.this, progressChanged + " Study Buddies per Hour", 
                        Toast.LENGTH_SHORT).show();
            }
        });
        
        
        // expand list view for group select
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_multiple_choice, prepareList()); 
        
        
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // allot our list to have multiple choice boxes
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
    
    public String[] prepareList(){
    	
    	listContent[0] = "hi";
    	listContent[1] = "dfdafd";
    	// get the list of groups here
    	
    	return listContent;
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
}