package com.androidstudio.snowman.auxiliary;

import java.util.ArrayList;

import com.androidstudio.snowman.MainActivity;
import com.androidstudio.snowman.R;

import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener {

	MainActivity main;
	ListView list;
	DrawerLayout drawer;
	
	// Constructor
	public DrawerItemClickListener(MainActivity main, ListView list, DrawerLayout drawer) {
		this.main = main;
		this.list = list;
		this.drawer = drawer;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// Highlight the selected item
	    list.setItemChecked(position, true);
	    
	    // Change currentGroup
//	    main.setCurrentGroup(list.getItemAtPosition(position).toString());
	    
	    // get cards of the new group from the database
	    ArrayList<Card> cards = MainActivity.cardhandler.getGroupCards(main.getCurrentGroup());
	    
	    // regenerate cards in main activity
	    main.setCards(cards);
	    
	    // regenerate fragments in main activity
	    main.getViewPager().removeAllViews(); // remove all the old views
	    main.getFragments(main.getCards());
	    
	    // notify the adapters that the data have changed 
	    main.getViewPager().setAdapter(null);
	    main.getViewPager().setAdapter(new PagerAdapter(main, main.getFragments()));
	    
	    main.getViewPager().getAdapter().notifyDataSetChanged();
	    ((GridViewAdapter) main.getGridView().getAdapter()).notifyDataSetChanged();
	    
	    // Change the main view of the main activity
	    if(MainActivity.isGridViewOn) {
	    	main.getMainPage().removeView(main.getGridView());	
	    } else {
	    	MainActivity.isGridViewOn = true;
	    	main.getMainPage().removeView(main.getViewPager());
	    }
	    main.getMainPage().addView(main.getGridView(), main.getIndexOfView());
	    
	    // Close the drawer
	    drawer.closeDrawer(list);
		
	}

}
