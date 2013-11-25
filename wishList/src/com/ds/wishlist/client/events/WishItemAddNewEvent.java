package com.ds.wishlist.client.events;

import com.ds.wishlist.client.events.handlers.WishItemAddNewHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

public class WishItemAddNewEvent  extends GwtEvent<WishItemAddNewHandler> {

	public static Type<WishItemAddNewHandler> TYPE = new Type<WishItemAddNewHandler>();	

	public WishItemAddNewEvent() {	
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WishItemAddNewHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(WishItemAddNewHandler handler) {
		handler.onEvent(this);
	}
}