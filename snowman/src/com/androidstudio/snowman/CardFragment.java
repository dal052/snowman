package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.Card;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CardFragment extends Fragment {
	private TextView text;
	
	private static final String CARD = "com.androidstudio.snowman.CARD";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get message
		Card card = (Card) getArguments().getParcelable(CARD);
		
		// Inflate view
		View view = (View) inflater.inflate(R.layout.card_fragment, container, false);
	
		// Set text on the TextView
		text = (TextView) view.findViewById(R.id.text);
		text.setText(card.getFront());
	
		return view;
	}
	
	public static CardFragment newInstance(Card card) {
		CardFragment cardFrag = new CardFragment();
		Bundle args = new Bundle();
		
		// Put extra
		args.putParcelable(CARD, card);
		cardFrag.setArguments(args);
		
		return cardFrag;
	}

}
