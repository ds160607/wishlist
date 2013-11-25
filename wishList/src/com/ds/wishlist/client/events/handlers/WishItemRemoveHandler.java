package com.ds.wishlist.client.events.handlers;

import com.ds.wishlist.client.events.WishItemRemoveEvent;
import com.google.gwt.event.shared.EventHandler;

public interface WishItemRemoveHandler  extends EventHandler {
	 void onEvent(WishItemRemoveEvent event);
}
