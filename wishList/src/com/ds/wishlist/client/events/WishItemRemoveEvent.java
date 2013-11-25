package com.ds.wishlist.client.events;

import com.ds.wishlist.client.WishItem;
import com.ds.wishlist.client.events.handlers.WishItemRemoveHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WishItemRemoveEvent extends GwtEvent<WishItemRemoveHandler> {

	public static Type<WishItemRemoveHandler> TYPE = new Type<WishItemRemoveHandler>();	
	private WishItem wishItem = null;
	
	public WishItemRemoveEvent(WishItem wi) {
		wishItem = wi;
	}

	public WishItem getWishItem () {
		return wishItem;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WishItemRemoveHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(WishItemRemoveHandler handler) {
		handler.onEvent(this);
	}
}
