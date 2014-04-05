package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {

	private String currentGroup;
	ParseObject studybuddy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);
		

		currentGroup = getIntent().getStringExtra(MainActivity.CURRENTGROUP);

//		studybuddy = new ParseObject("studybuddies");


		//		ParseObject testObject = new ParseObject("TestObject");
		//		testObject.put("foo", "bar");
		//		testObject.saveInBackground();

	}

	public void addCard(View view){
		EditText cardBack = (EditText) findViewById(R.id.enter_back);
		EditText cardFront = (EditText) findViewById(R.id.enter_front);

		//add card to database and show on screen based on clicked button
		switch(view.getId()){
		case R.id.click_post:
			Card newCard = new Card(currentGroup, cardFront.getText().toString(),
					cardBack.getText().toString());
			MainActivity.cardhandler.createCard(newCard);
			MainActivity.changeInDatabase = true;
			
//			studybuddy.put("DECK", currentGroup);
//			studybuddy.put("FRONT_PAGE", cardFront.getText().toString());
//			studybuddy.put("BACK_PAGE", cardBack.getText().toString());
//			studybuddy.saveInBackground();

			new addCardtoParseServer().execute();
			
			break;
		case R.id.click_cancel:
			break;
		}	
		finish();		
	}
	
	
	private class addCardtoParseServer extends AsyncTask<Void, Void, Void>{

		EditText cardBack = (EditText) findViewById(R.id.enter_back);
		EditText cardFront = (EditText) findViewById(R.id.enter_front);

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			ParseObject studybuddy = new ParseObject("studybuddies");
			// Save data to Parse.com :]
			studybuddy.put("DECK", currentGroup);
			studybuddy.put("FRONT_PAGE", cardFront.getText().toString());
			studybuddy.put("BACK_PAGE", cardBack.getText().toString());
			try {
				studybuddy.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_card, menu);
		return true;
	}


	

}