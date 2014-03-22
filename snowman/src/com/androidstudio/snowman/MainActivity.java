package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.CardHandler;
import com.androidstudio.snowman.auxiliary.DrawerItemClickListener;
import com.androidstudio.snowman.auxiliary.GridViewAdapter;
import com.androidstudio.snowman.auxiliary.PagerAdapter;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.Window;

import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	public static CardHandler cardhandler;
	public static boolean changeInDatabase = false;
	public static boolean addNewCard = false;
	final public static String CURRENTGROUP = "com.androidstudio.snowman.MainActivity.CURRENTGROUP";
	private static boolean firstOpen = true;

	private static MainActivity instance;
	private ViewPager pager;
	private PagerAdapter adapter;

	private String[] groups;
	private String currentGroup;

	private DrawerLayout drawer;
	private ListView drawerList;

	private GridView gridViewOfCards;
	private View gridview; // gridview layout

	private ArrayList<Card> cards;
	private ArrayList<CardFragment> fragments;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_main);



		// get layout that is used for gridview
		gridview = getLayoutInflater().inflate(R.layout.viewcards_grid, null);

		// splash screen

		if(firstOpen){
			Intent splash = new Intent(this, SplashActivity.class);
			startActivity(splash);
			// start service
			
			startService(new Intent(this, NotiService.class));
			firstOpen=false;
		}

		//		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutombar); 

		/*
		int colors[] = {0x00ACED, 0x27DADF};
//		ActionBar actionbar = getActionBar();
		View layout = findViewById(R.id.mainlayout);
		GradientDrawable gradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
		gradient.setCornerRadius(0f);
//		actionbar.setBackgroundDrawable(gradient);
		layout.setBackgroundDrawable(gradient); 

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.cutombar);
	    View title = getWindow().findViewById(R.id.mainlayout);
	    title.setBackgroundDrawable(gradient); */



		// Set things for drawer to work
		groups = getResources().getStringArray(R.array.groups);
		currentGroup = groups[0];

		// initialize layouts
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		gridViewOfCards = (GridView) gridview.findViewById(R.id.gridview);

		currentGroup = groups[0];

		// Set up adapter for ListView
		drawerList.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, groups));
		drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerList, drawer));



		//get cardhandler to store in data base
		cardhandler = new CardHandler(this);
		cardhandler.open();

		// Set up list for cards
		cards = cardhandler.getGroupCards(currentGroup);
		//		cards = cardhandler.getAllCards();

		// Set up list for fragments
		fragments = new ArrayList<CardFragment>();
		getFragments(fragments);

		// Set up pager
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new PagerAdapter(this, fragments);
		pager.setAdapter(adapter);


		// Set up adapter and onClickListener for ListView; 
		// This ListView is used in DrawerLayout to show the group menu
		drawerList.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, groups));
		drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerList, drawer));

		if(gridViewOfCards != null)
		// Set up adapter and onClickListener for GridView
		gridViewOfCards.setAdapter(new GridViewAdapter(this, cards));

	}

	@Override
	protected void onResume() {
		super.onResume();

		if(changeInDatabase) {
			Card newCard = cardhandler.lastCard();
			cards.add(newCard);

			// add a new fragment
			fragments.add(CardFragment.newInstance(newCard));
			pager.getAdapter().notifyDataSetChanged();

			// set the current card to the new card
			pager.setCurrentItem(fragments.size() - 1);

			changeInDatabase = false;
		}
	}

	@Override
	public void onBackPressed() {
	    // if group menu bar is on change to card view
//		if(gridview.getVisibility() == View.VISIBLE) {
//		Log.w("debug", "i'm here");
//		Log.w("debug", "visibility" + gridview.getVisibility());
//		setContentView(R.layout.activity_main);
//	} else
//		Log.w("debug", "i'm here");
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		return true;
	}

	//getter for card
	public ArrayList<Card> getCards() {
		return cards;
	}

	//getter for fragment
	public ArrayList<CardFragment> getFragments() {
		return fragments;
	}
	
	// getter for gridview
	public View getGridview() {
		return gridview;
	}

	//allow card to swipe back and forth 
	private void getFragments(ArrayList<CardFragment> fragments) {
		for(int i=0; i<cards.size(); ++i) {
			fragments.add(CardFragment.newInstance(cards.get(i)));
		}
	}	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case(R.id.action_new):

			Intent intent = new Intent(this, AddCardActivity.class);

			intent.putExtra(CURRENTGROUP, currentGroup);
			startActivity(intent);
			return true;

		case(R.id.action_seekbar):

			Intent seekIntent = new Intent(this, SeekbarActivity.class);
			startActivity(seekIntent);
			return true;

		}
		return super.onOptionsItemSelected(item);
	}


}
