package com.ds.wishlist.client.events.handlers;

import com.ds.wishlist.client.events.ListUpdateEvent;
import com.google.gwt.event.shared.EventHandler;

public interface  ListUpdatedHandler extends EventHandler {
	 void onEvent(ListUpdateEvent event);
}
