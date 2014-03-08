package com.androidstudio.snowman.auxiliary;

import com.androidstudio.snowman.MainActivity;
import com.androidstudio.snowman.R;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
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
//	    main.currentGroup = list.getItemAtPosition(position);
	    
	    // regenerate cards in main activity 
	    // regenerate fragments in main activiy
	    
	    // Change the layout of the main activity
	    main.setContentView(main.getGridview());
	    
	    // Close the drawer
	    drawer.closeDrawer(list);
		
	}

}
