package com.androidstudio.snowman.auxiliary;


import java.util.ArrayList;

import com.androidstudio.snowman.R;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

	Context context;
	ArrayList<Card> cards;

	// Constructor
	public GridViewAdapter(Context context, ArrayList<Card> cards) {
		this.context = context;
		this.cards = cards;
	}
	
	@Override
	public int getCount() {
		return 2*cards.size();
//		return 6;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	// generate view for gridView layout
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView cardDescription;
		
		if(convertView == null) {
			cardDescription = new TextView(context);
			
			// set layout parameters for the textView
			cardDescription.setGravity(Gravity.CENTER);
			cardDescription.setBackgroundResource(R.color.white);
			cardDescription.setEllipsize(TruncateAt.END);
			cardDescription.setLines(4);			
			
		} else {
			cardDescription = (TextView) convertView;
		}
				
		// from the arrayList of the cards, get front and back descriptions
		if(position%2 == 0)
			cardDescription.setText(cards.get(position/2).getFront());
		else 
			cardDescription.setText(cards.get(position/2).getBack());
		
		return cardDescription;
	}

}
