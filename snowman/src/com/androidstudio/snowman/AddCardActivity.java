package com.androidstudio.snowman;

import java.util.ArrayList;

import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.CardHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddCardActivity extends Activity {
	private MainActivity mainActivity; 
	private ArrayList<Card> cards;
	private ArrayList<CardFragment> fragments;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card);	
	}
	
	public void addCard(View view){
		
		EditText cardAns = (EditText) findViewById(R.id.enter_answer);
		EditText cardDesc = (EditText) findViewById(R.id.enter_description);
		
		
		switch(view.getId()){
		case R.id.click_post:
			Card newCard = new Card("", cardAns.getText().toString(),
					cardDesc.getText().toString());
			MainActivity.cardhandler.createCard(newCard);
			
			cards = mainActivity.getCards();
			cards.add(newCard);
			fragments = mainActivity.getFragments();
			fragments.add(CardFragment.newInstance(cards.get(cards.size()-1)));
					
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