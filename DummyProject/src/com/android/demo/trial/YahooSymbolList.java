package com.android.demo.trial;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class YahooSymbolList extends ArrayList<YahooSymbol> implements Parcelable{

	public YahooSymbolList(){}
	
	public YahooSymbolList(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
	
		public YahooSymbolList createFromParcel(Parcel in) {
             return new YahooSymbolList(in);
        }
	
        public Object[] newArray(int arg0) {
             return null;
        }
    };
	
    private void readFromParcel(Parcel in){
    	this.clear();
    	
    	//read the list size
    	int size = in.readInt();
    	
    	// remeber the order in writeToParcel and follow  the same
    	for (int i = 0; i < size; ++i){
    		YahooSymbol symbol = new YahooSymbol();
    		symbol.setSymbol(in.readString());
    		symbol.setName(in.readString());
    		symbol.setExch(in.readString());
    		symbol.setType(in.readString());
    		this.add(symbol);
    	}
    }
    @Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
    	int size = this.size();
    	// write the list size;
    	dest.writeInt(size);
    	
    	for (int i = 0; i < size; ++i)
    	{
    		YahooSymbol symbol = this.get(i);
    		
    		dest.writeString(symbol.getSymbol());
    		dest.writeString(symbol.getName());
    		dest.writeString(symbol.getExch());
    		dest.writeString(symbol.getType());
    	}
	}

}
