package com.ds.wishlist.client.events;


import com.ds.wishlist.client.WishItem;
import com.ds.wishlist.client.events.handlers.WishItemSaveItemHandler;
import com.google.gwt.event.shared.GwtEvent;

public class WishItemSaveItemEvent extends GwtEvent<WishItemSaveItemHandler> {

	public static Type<WishItemSaveItemHandler> TYPE = new Type<WishItemSaveItemHandler>();	
	private WishItem wishItem = null;
	
	public WishItemSaveItemEvent(WishItem wi) {
		wishItem = wi;
	}

	public WishItem getWishItem () {
		return wishItem;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<WishItemSaveItemHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(WishItemSaveItemHandler handler) {
		handler.onEvent(this);
	}
}