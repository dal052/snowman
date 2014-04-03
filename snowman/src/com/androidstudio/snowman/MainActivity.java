package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.CardHandler;
import com.androidstudio.snowman.auxiliary.DrawerItemClickListener;
import com.androidstudio.snowman.auxiliary.GridViewAdapter;
import com.androidstudio.snowman.auxiliary.PagerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.security.auth.Destroyable;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	/* *************************************************************** */	
	/* *************************************************************** */
	/* ************************** Variables ************************** */
	/* *************************************************************** */
	/* *************************************************************** */
	final public static String CURRENTGROUP = "com.androidstudio.snowman.MainActivity.CURRENTGROUP";
	public static CardHandler cardhandler;
	public static boolean changeInDatabase = false; // if there is a change in database, update viewpager
	public static boolean isGridViewOn = true; // to see if the main view is on grid view
	
	private static boolean firstOpen = true;
	final private String CURRENTCARD = "com.adnroidstudio.snowman.MainActivity.CURRENTCARD";
	final private String GROUPSPREFS = "com.androidstudio.snowman.MainActivity.GROUPSPREFS";
	final private CharSequence drawerTitle = "Choose a Deck"; 
	
	private SharedPreferences prefs;
	private Set<String> groups;
	private static String currentGroup;

	private ViewPager pager;
	private DrawerLayout drawer;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	
	private GridView gridView;
	private ViewGroup mainPage; // main layout
	private View mainView; // main view that is changed
	private int indexOfView; // index of main view
	
	private ArrayList<Card> cards;
	private ArrayList<CardFragment> fragments;

	
	/* *************************************************************** */	
	/* *************************************************************** */
	/* *************************** Methods *************************** */
	/* *************************************************************** */
	/* *************************************************************** */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		
		mainPage = (ViewGroup) findViewById(R.id.drawer_layout);
		// get view that is used for gridview and viewpager
		mainView = findViewById(R.id.mainView);
		indexOfView = mainPage.indexOfChild(mainView);

		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutombar); 

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
		// get groups from sharedPreferrence
		prefs = getSharedPreferences(GROUPSPREFS, Context.MODE_PRIVATE);
		// make groups
		groups = new LinkedHashSet<String>();
		
		// get name of groups from sharedPreferences
		try {
			JSONArray groupsJSON = new JSONArray(prefs.getString("groups", "[\"SAT Vocab\", \"My Deck\"]"));
			
			// get the names of the group
			for(int i=0; i<groupsJSON.length(); i++) {
				groups.add(groupsJSON.getString(i));
			}
			
			// if there is no groups
			if(groupsJSON.length() == 0) 
				groups.add("My Deck");
			
		} catch(JSONException error) {
			error.printStackTrace();
		}
		
		Log.w("debug", "this is what i get: " + prefs.getString("groups", "default"));
		
		// decide the current group 
		if(savedInstanceState == null) {
			String receivedGroup = (String) groups.toArray()[0];
			if(firstOpen)
				currentGroup = receivedGroup;
			else
				// get from sharedPreference
				currentGroup = prefs.getString(CURRENTGROUP, receivedGroup);
			
		} else
			currentGroup = savedInstanceState.getString(CURRENTGROUP);
		
		setTitle("Study Buddy - " + currentGroup); // Set the title of the app
		
		//get cardhandler to store in data base
		cardhandler = new CardHandler(this);
		cardhandler.open();

		// Set up list for cards
		cards = cardhandler.getGroupCards(currentGroup);
		//		cards = cardhandler.getAllCards();

		// Set up list for fragments
		fragments = new ArrayList<CardFragment>();
		getFragments(cards);
		
		// initialize grid view
		gridView = (GridView) getLayoutInflater().inflate(R.layout.mainview_gridview, null);
		
		// Set up adapter and onClickListener for GridView
		GridViewAdapter gridViewAdapter = new GridViewAdapter(this, this);
		gridView.setAdapter(gridViewAdapter);
		gridView.setOnItemClickListener(gridViewAdapter);
		
		// initialize layouts
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		// Set up adapter and onClickListener for ListView; 
		// This ListView is used in DrawerLayout to show the group menu
		drawerList.setAdapter(new ArrayAdapter<String>(
				this, android.R.layout.simple_list_item_1, groups.toArray(new String[0])));
		drawerList.setOnItemClickListener(new DrawerItemClickListener(this, drawerList, drawer));

		// action bar drawer toggle
		// this is for actionbar and drawerlayout actions
		drawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				drawer,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description */
				R.string.drawer_close  /* "close drawer" description */
				) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle("Study Buddy - " + currentGroup);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		// Set the drawer toggle as the DrawerListener
		drawer.setDrawerListener(drawerToggle);

		// Set up pager
		pager = (ViewPager) getLayoutInflater().inflate(R.layout.mainview_viewpager, null);
		pager.setAdapter(new PagerAdapter(this, fragments));
		
		// set the current card if there was a card that was being viewed
		if(savedInstanceState != null)
			pager.setCurrentItem(savedInstanceState.getInt(CURRENTCARD));
			
		getActionBar().setDisplayHomeAsUpEnabled(true);		
		
		if(!isGridViewOn)
			mainPage.addView(pager, indexOfView);
		else
			mainPage.addView(gridView, indexOfView);

		// splash screen
		if(firstOpen){
			Intent splash = new Intent(this, SplashActivity.class);
			startActivity(splash);
			// start service
			int progress = getSharedPreferences(SeekbarActivity.NOTIPREFS, Context.MODE_PRIVATE)
						.getInt(SeekbarActivity.NOTIINT, 20);
			Intent notiIntent = new Intent(this, NotiService.class);
			notiIntent.putExtra(SeekbarActivity.NOTIINT, progress);
			if(progress != 0)
				startService(notiIntent);
			firstOpen=false;
		}
	} // end of onCreate


	/* *************************************************************** */	
	/* *************************************************************** */
	/* ********************** Override Methods *********************** */
	/* *************************************************************** */
	/* *************************************************************** */
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
		if(isGridViewOn) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString(CURRENTGROUP, currentGroup);
			editor.commit();
			super.onBackPressed();
			
		} else {
			isGridViewOn = true;
			mainPage.removeView(pager);
			mainPage.addView(gridView, indexOfView);
		}
	}
	
	@Override
	protected void onDestroy() {
		// save preferecnes
		SharedPreferences.Editor editor = prefs.edit();
		// save groups
		JSONArray groupsJSON = new JSONArray();
		for(String string : groups) {
			groupsJSON.put(string);
			Log.w("debug", "i'm here to destroy " + string);
		}
			
		editor.putString("groups", groupsJSON.toString());
		editor.commit();
		Log.w("debug", "i'm here to destroy ");
		
		// close database
		cardhandler.close();
		
		super.onDestroy();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	// deals with configuration: 
	// Configuration.ORIENTATION_LANDSCAPE / ORIENTATION_PORTRAIT
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
	
	 /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = drawer.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_new).setVisible(!drawerOpen);
        menu.findItem(R.id.action_seekbar).setVisible(!drawerOpen);
        menu.findItem(R.id.action_newGroup).setVisible(drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case(R.id.action_new):
			// create a new card
			Intent intent = new Intent(this, AddCardActivity.class);

			intent.putExtra(CURRENTGROUP, currentGroup);
			startActivity(intent);
			return true;

		case(R.id.action_seekbar):
			// change notification settings
			Intent seekIntent = new Intent(this, SeekbarActivity.class);
			startActivity(seekIntent);
			return true;
		
		case(R.id.action_newGroup):
			// create a new group

			// open dialog for user input
//			AlertDialog.Builder alert = new AlertDialog.Builder(
//					new ContextThemeWrapper(this, R.style.NewDialog));
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Create A New Deck");
//			alert.setMessage("Type in the name of the deck");

			
			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);
			
			// When creating a new group
			alert.setPositiveButton("Create", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String newGroupName = input.getText().toString();
					
					// update groups list with new group name
					groups.add(newGroupName);
//					((BaseAdapter) drawerList.getAdapter()).notifyDataSetChanged();
					drawerList.setAdapter(null);
					drawerList.setAdapter(new ArrayAdapter<String>(
							MainActivity.this, android.R.layout.simple_list_item_1, groups.toArray(new String[0])));
					
					// Maybe you don't have to do the bottom
					// change gridView and viewPager to new group

				}
			});

			alert.setNegativeButton("Cancel", null);
			
			AlertDialog dialog = alert.create();
			dialog.show();
			final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
			
			// Add textWatcher to the editor to check for duplicate
			input.addTextChangedListener(new TextWatcher() {
				@Override
				public void afterTextChanged(Editable s) {
					String newGroupName = input.getText().toString();
					
					// check for duplicate and respond accordingly					
					if(!positiveButton.isEnabled())
						positiveButton.setEnabled(true);
					else if(groups.contains(newGroupName)) {
						Toast.makeText(getApplicationContext(), "There is a duplicate deck", Toast.LENGTH_SHORT).show();
						positiveButton.setEnabled(false);
					}
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}
			});

			return true;
		
		case(R.id.action_deleteGroup):
			// delete current group

			// TODO:
			// delete all the cards in this group from the database
			// update groups list 
			// show group menu
			
			return true;
			
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		currentGroup = savedInstanceState.getString(CURRENTGROUP);
		super.onRestoreInstanceState(savedInstanceState);
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save the current card 
		outState.putInt(CURRENTCARD, pager.getCurrentItem());
		outState.putCharSequence(CURRENTGROUP,currentGroup);
	}


	/* *************************************************************** */
	/* *************************************************************** */
	/* ********************** Getters & Setters ********************** */
	/* *************************************************************** */
	/* *************************************************************** */
	//getter for cards
	public ArrayList<Card> getCards() {
		return cards;
	}

	//setter for cards
	public void setCards(ArrayList<Card> cards) {
		this.cards.clear();
//		this.cards = cards;
		for(Card card : cards)
			this.cards.add(card);
	}
	
	//getter for fragments
	public ArrayList<CardFragment> getFragments() {
		return fragments;
	}

	//allow card to swipe back and forth 
	public void getFragments(ArrayList<Card> cards) {
		fragments.clear();
		for(int i=0; i<cards.size(); ++i) {
			fragments.add(CardFragment.newInstance(cards.get(i)));
		}
	}	

	// getter for gridView
	public GridView getGridView() {
		return gridView;
	}

	// getter for viewPager
	public ViewPager getViewPager() {
		return pager;
	}

	// getter for mainPage
	public ViewGroup getMainPage() {
		return mainPage;
	}

	// getter for mainPage
	public int getIndexOfView() {
		return indexOfView;
	}

	// getter for current group 
	public String getCurrentGroup() {
		return currentGroup;
	}

	// setter for current group
	public void setCurrentGroup(String currentGroup) {
		this.currentGroup = currentGroup;
	}

	// getter for cardHandler
	public static CardHandler getCardHandler() {
		return cardhandler;
	}

	// setter for cardHandler
	public static void setCardHandler(CardHandler cardhandler) {
		MainActivity.cardhandler = cardhandler;
	}

}
