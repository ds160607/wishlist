package com.ds.wishlist.client.events.handlers;

import com.ds.wishlist.client.events.WishItemSaveItemEvent;
import com.google.gwt.event.shared.EventHandler;

public interface WishItemSaveItemHandler extends EventHandler {
	 void onEvent(WishItemSaveItemEvent event);
}

