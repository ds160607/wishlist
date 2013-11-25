package com.ds.wishlist.client;

public class Picture {
	private String url;
	private String data = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAEElEQVQIHQEFAPr/AAAAAAAABQABuokQigAAAABJRU5ErkJggg==";
		
	public Picture(String url) {
		this.url=url;
	}
	
	
	public String getURL(){
		return url;
	}
	
	public void setData(String dt){
		this.data = dt;
	}
	public String getData(){
		return data;
	}
}
