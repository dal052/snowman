package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.CardHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {
	private CardHandler cardhandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);
		
	}
	
	public void createCard(View view){
		Intent cards = new Intent(this, MainActivity.class);
		EditText cardAns = (EditText) findViewById(R.id.enter_answer);
		EditText cardDesc = (EditText) findViewById(R.id.enter_description);
		
		Card newCard = new Card("", cardAns.getText().toString(),
							 cardDesc.getText().toString() );
		cardhandler.createCard(newCard);
		startActivity(cards);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_card, menu);
		return true;
	}

}