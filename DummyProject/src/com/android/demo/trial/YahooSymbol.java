package com.android.demo.trial;

import android.os.Parcel;
import android.os.Parcelable;

public class YahooSymbol{

	String symbol;
	String name;
	String exch;
	String type;
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExch() {
		return exch;
	}
	
	public void setExch(String exch) {
		this.exch = exch;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
