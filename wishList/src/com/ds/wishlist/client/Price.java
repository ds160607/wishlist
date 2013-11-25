package com.ds.wishlist.client;

public class Price {
	
	private String price;
	private Currency currency = Currency.RUB;
	
	public Price(String p, Currency c){
		this.price=p;
		this.currency=c;
	}
	
	public String getPrice(){
		return price;
	}
	public Currency getCurrency(){
		return currency;
	}
}
