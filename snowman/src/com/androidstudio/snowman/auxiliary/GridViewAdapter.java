package com.androidstudio.snowman.auxiliary;


import java.util.ArrayList;

import com.androidstudio.snowman.MainActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GridViewAdapter extends BaseAdapter {

	MainActivity main;
	ArrayList<Card> cards;
	
	public GridViewAdapter(MainActivity main) {
		this.main = main;
		this.cards = main.getCards();
	}
	
	@Override
	public int getCount() {
		return cards.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		return null;
	}

}
