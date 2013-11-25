package com.ds.wishlist.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class HostPoint implements EntryPoint {

	private WishListWidget myWidget;
	private final EventBus eventBus = new SimpleEventBus();
	private ProviderLocalStorage providerLS;

	public final Storage localStore = Storage.getLocalStorageIfSupported();

	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();

		myWidget = new WishListWidget(eventBus);
		rootPanel.add(myWidget);
		
		String value = Window.Location.getParameter("edit");
		if (value.equals("yes")) {
			myWidget.editFirst();
		}
		
		/*
		 * Create a data provider dataProvider will generate a ListUpdateEvent
		 * so that will force WishListWidget to refresh the list.
		 */
		providerLS = new ProviderLocalStorage(eventBus);
		resetNewItem();
		
		
		// exportMyFunction();

		/*
		 * WishItem m = new WishItem(); m.setDescription("description");
		 * m.setNotes("notes"); m.setReason("reason"); m.setWhere("where");
		 * myWishList.addItem(m);
		 */

		// myWishList.setList(readWishList());

		/*
		 * eventBus.addHandler(WishItemSaveItemEvent.TYPE, new
		 * WishItemSaveItemHandler() { public void onEvent(WishItemSaveItemEvent
		 * event) { WishItem wi = event.getWishItem(); //saveWishItem(wi);
		 * 
		 * } });
		 */
	}

	public static native void resetNewItem()/*-{
		if ("extension" in chrome) {		
			chrome.extension.sendMessage({
			'messageType' : 'resetNewItem',
			}, function(response) {
			});
		}		
	}-*/;

	// public static native void exportMyFunction() /*-{
	// $wnd.myFunction =
	// $entry(@com.ds.wishlist.client.HostPoint::fireUpdate());
	// }-*/;

	private void fireUpdate() {
		providerLS.firedUpdate();
	}
	/*
	 * private void saveWishItem(WishItem wi) { if (localStore != null) {
	 * localStore.setItem( APP_STORAGE_PREFIX + "." + ITEM_PREFIX + "_1234",
	 * wi.toJSON()); myWishList.setList(readWishList()); eventBus.fireEvent(new
	 * ListUpdateEvent()); } }
	 * 
	 * private LinkedList<WishItem> readWishList() { LinkedList<WishItem> list =
	 * new LinkedList<WishItem>(); if (localStore != null) { for (int i = 0; i <
	 * localStore.getLength(); i++) { String key = localStore.key(i); if
	 * (key.startsWith(APP_STORAGE_PREFIX + "." + ITEM_PREFIX)) { JSONValue
	 * value = JSONParser.parseStrict(localStore .getItem(key)); JSONObject
	 * wishObject = value.isObject();
	 * 
	 * WishItem m = new WishItem();
	 * m.setId(wishObject.get("id").isString().stringValue());
	 * m.setDescription(wishObject.get("description").isString()
	 * .stringValue());
	 * m.setNotes(wishObject.get("notes").isString().stringValue());
	 * m.setReason(wishObject.get("reason").isString() .stringValue());
	 * m.setWhere(wishObject.get("where").isString().stringValue());
	 * 
	 * list.add(m);
	 * 
	 * } } } return list; }
	 */

}