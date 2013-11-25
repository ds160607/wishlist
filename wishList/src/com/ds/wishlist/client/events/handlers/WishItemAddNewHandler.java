package com.ds.wishlist.client.events.handlers;

import com.ds.wishlist.client.events.WishItemAddNewEvent;
import com.google.gwt.event.shared.EventHandler;

public interface  WishItemAddNewHandler extends EventHandler {
	 void onEvent(WishItemAddNewEvent event);
}
