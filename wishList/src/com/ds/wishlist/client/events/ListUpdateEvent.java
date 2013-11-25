package com.ds.wishlist.client.events;

import com.ds.wishlist.client.WishList;
import com.ds.wishlist.client.events.handlers.ListUpdatedHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ListUpdateEvent extends GwtEvent<ListUpdatedHandler> {

	private WishList wishList = null;
	
	public static Type<ListUpdatedHandler> TYPE = new Type<ListUpdatedHandler>();	

	public ListUpdateEvent(WishList wl) {
		wishList = wl;
	}
	public WishList getWishList () {
		return wishList;
	}
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ListUpdatedHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ListUpdatedHandler handler) {
		handler.onEvent(this);
	}

}
