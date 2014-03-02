package com.androidstudio.snowman;

import com.androidstudio.snowman.aux.Card;

import com.androidstudio.snowman.aux.PagerAdapter;
import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.CardHandler;
import com.androidstudio.snowman.auxiliary.PagerAdapter;


import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	public static CardHandler cardhandler;
	public static boolean addNewCard = false;
	
	private static MainActivity instance;
	private ViewPager pager;
	private PagerAdapter adapter;
	
	private String[] groups;
	private DrawerLayout drawer;
	private ListView drawerList;
	
	private ArrayList<Card> cards;
	private ArrayList<CardFragment> fragments;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		instance = this;
		
		// Set things for drawer to work
		groups = getResources().getStringArray(R.array.groups);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		
		// Set up adapter for ListView
		drawerList.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, groups));
		
		cardhandler = new CardHandler(this);
		cardhandler.open();
		/*
		cardhandler.createCard(new Card("Group 1", "hahaha", "hohoho"));
		cardhandler.createCard(new Card("Group 1", "hellehele", "hohoho"));
		cardhandler.createCard(new Card("Group 1", "whowhwoho", "hohoho"));
		*/
		// Set up list for cards
		cards = cardhandler.getAllCards();
		
		// Set up list for fragments
		fragments = new ArrayList<CardFragment>();
		getFragments(fragments);
		
		// Set up pager
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new PagerAdapter(this, fragments);
		pager.setAdapter(adapter);
		

		// start service
		startService(new Intent(this, NotiService.class));
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		if(addNewCard) {
			Card newCard = cardhandler.lastCard();
			cards.add(newCard);
		
			// add a new fragment
			fragments.add(CardFragment.newInstance(newCard));
			pager.getAdapter().notifyDataSetChanged();
			
			// set the current card to the new card
			pager.setCurrentItem(fragments.size() -1);
			
			addNewCard = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		return true;
	}
	
	/*private void getCards(ArrayList<Card> cards) {
		for(int i=1; i<=numberOfCards; ++i) {
			cards.add(new Card(
					"Group 1", 
					"Card " + i + "\nUsing this approach, you need to keep track of the string tags " +
							"and associate them with all the fragment pages. You could use a map to store " +
							"each tag along with the current page index, which is set at the time when the " +
							"fragment page is instantiated.\n",
					"Back of Card"));
		}
	}*/

/*	private void getCards(ArrayList<Card> cards){
		if(cards.size() == 0){
			Intent intent = new Intent(this, AddCardActivity.class);
			startActivity(intent);
		}
	}*/
	
	public ArrayList<Card> getCards() {
		return cards;
	}


	}

	public ArrayList<CardFragment> getFragments() {
		return fragments;
	}

	

	private void getFragments(ArrayList<CardFragment> fragments) {
		for(int i=0; i<cards.size(); ++i) {
			fragments.add(CardFragment.newInstance(cards.get(i)));
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		//startService(new Intent(this, NotiService.class));
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	
	public static synchronized MainActivity getMainActivity() {
		return instance;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case(R.id.action_new):
			Intent intent = new Intent(this, AddCardActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	
	
	
	
	
}
