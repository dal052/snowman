package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);
	}
	
	public void addCard(View view){
		EditText cardBack = (EditText) findViewById(R.id.enter_back);
		EditText cardFront = (EditText) findViewById(R.id.enter_front);
		
		//add card to database and show on screen based on clicked button
		switch(view.getId()){
		case R.id.click_post:
			Card newCard = new Card("", cardBack.getText().toString(),
					cardFront.getText().toString());
			MainActivity.cardhandler.createCard(newCard);
			MainActivity.addNewCard = true;
			break;
		case R.id.click_cancel:
			break;
		}		
		finish();		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_card, menu);
		return true;
	}

	
	

}