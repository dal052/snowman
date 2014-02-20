package com.androidstudio.snowman.aux;

import android.os.Parcel;
import android.os.Parcelable;

public class Card implements Parcelable {
	private String group;
	private String front;
	private String back;
	private int id;
	
	public Card(String group, String front, String back) {
		this.group = group;
		this.front = front;
		this.back = back;
	}
	
	 /**
     * Default Constructor
     */
    public Card() {}

    /**
     * Getter and setters for all instance variables
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getFront() {
    	return front;
    }
    
    public void setFront(String front){
    	this.front = front;
    }
    
    public String getBack() {
    	return back;
    }
    
    public void setBack(String back) {
    	this.back = back;
    }

//I forgot to save the rest....TT

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
}