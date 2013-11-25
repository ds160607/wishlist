package com.ds.wishlist.client;

import java.util.LinkedList;

import com.ds.wishlist.client.events.ListUpdateEvent;
import com.ds.wishlist.client.events.WishItemAddNewEvent;
import com.google.gwt.event.shared.EventBus;

public class WishList {
	
	private LinkedList<WishItem> list = new LinkedList<WishItem>();
	//private EventBus eventBus;
	
	public WishList () {	
		//eventBus = ev;
	}
	
	public void insertItem(WishItem item, WishItem pos) {
		int i = list.indexOf(pos);		
		list.add(i, item);		
	}
	
	public void addItem(WishItem item){
		list.add(item);
		//eventBus.fireEvent(new ListUpdateEvent());
	}
	
	public void setList(LinkedList<WishItem> lst){
		this.list = lst;
		//eventBus.fireEvent(new ListUpdateEvent());
	}
	
	public void clear(){
		list.clear();
		//eventBus.fireEvent(new ListUpdateEvent());
	}
	
	public int getSize() {
		return list.size();
	}
	public WishItem getItem(int i){
		return list.get(i);
	}
	
}
