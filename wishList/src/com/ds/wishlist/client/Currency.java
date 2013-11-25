package com.ds.wishlist.client;

public enum Currency {
	
	USD("$"), RUB("р.");
	
	private String display = "";

	private Currency(String s) {
		this.display = s;
	}
	public String getDisplay(){
		return display;
	}
}
