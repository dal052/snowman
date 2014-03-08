package com.androidstudio.snowman;

import com.androidstudio.snowman.auxiliary.AnimationFactory;
import com.androidstudio.snowman.auxiliary.Card;
import com.androidstudio.snowman.auxiliary.AnimationFactory.FlipDirection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class CardFragment extends Fragment {
	private TextView frontText;
	private TextView backText;
	private ViewAnimator viewAnimator;
	private String front;
	private String back;
	
	private boolean showingBack = false;
	
	private static final String CARD = "com.androidstudio.snowman.CARD";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get message
		Card card = (Card) getArguments().getParcelable(CARD);
		
		// Inflate view
		View view = (View) inflater.inflate(R.layout.card_fragment, container, false);
		
		// Set text on the TextView
		viewAnimator = (ViewAnimator)view.findViewById(R.id.cardViewFlipper);
		frontText = (TextView) view.findViewById(R.id.frontText);
		backText = (TextView) view.findViewById(R.id.backText);
		
		myClickListener myClick = new myClickListener();
		viewAnimator.setOnClickListener(myClick);
		frontText.setOnClickListener(myClick);
		backText.setOnClickListener(myClick);
		
		front = card.getFront();
		back = card.getBack();
		
		frontText.setText(front);
		backText.setText(back);
	
		return view;
	}
	
	private class myClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			/*
			if(!showingBack)
				frontText.setText(back);
			else
				frontText.setText(front);
			
			showingBack = !showingBack;
			*/
			flipCard();
			
		}
		public void flipCard(){
			if(!showingBack){
				AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
			}
			else{
				AnimationFactory.flipTransition(viewAnimator, FlipDirection.LEFT_RIGHT);
			}
			
			showingBack = !showingBack;
		}
		
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
