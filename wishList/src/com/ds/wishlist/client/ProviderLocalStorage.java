package com.ds.wishlist.client;

import java.util.Date;
import java.util.LinkedList;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import com.ds.wishlist.client.events.ListUpdateEvent;
import com.ds.wishlist.client.events.WishItemRemoveEvent;
import com.ds.wishlist.client.events.WishItemSaveItemEvent;
import com.ds.wishlist.client.events.handlers.WishItemRemoveHandler;
import com.ds.wishlist.client.events.handlers.WishItemSaveItemHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageEvent;

public class ProviderLocalStorage {

	private WishList wishList = new WishList();
	private EventBus eventBus;
	private final Storage localStore = Storage.getLocalStorageIfSupported();
	public static String APP_STORAGE_PREFIX = "com.ds.wishlist";
	public static String ITEM_PREFIX = "wishitem";
	protected StorageEvent.Handler handler;

	// private Logger logger = Logger.getLogger("NameOfYourLogger");;
	public ProviderLocalStorage(EventBus eb) {
		
		eventBus = eb;
		// localStore.clear();
		eventBus.addHandler(WishItemSaveItemEvent.TYPE,
				new WishItemSaveItemHandler() {
					public void onEvent(WishItemSaveItemEvent event) {
						WishItem wi = event.getWishItem();
						saveWishItem(wi);
					}
				});

		eventBus.addHandler(WishItemRemoveEvent.TYPE,
				new WishItemRemoveHandler() {
					public void onEvent(WishItemRemoveEvent event) {
						WishItem wi = event.getWishItem();
						removeWishItem(wi);
					}
				});
		if (localStore != null) {
			handler = new StorageEvent.Handler() {
				public void onStorageChange(StorageEvent event) {
					// eventBus.fireEvent(new ListUpdateEvent(getWishList()));
					firedUpdate();
				}
			};
			
			localStore.addStorageEventHandler(handler);

			/*
			 * localStore.addStorageEventHandler(new StorageEvent.Handler() {
			 * public void onStorageChange(StorageEvent event) {
			 * eventBus.fireEvent(new ListUpdateEvent(getWishList())); } });
			 */
		}
		// eventBus.fireEvent(new ListUpdateEvent(getWishList()));
		firedUpdate();
	}

	public void removeWishItem(WishItem wi) {
		if (localStore != null) {
			if (wi.getId() != null) {
				localStore.removeItem(APP_STORAGE_PREFIX + "." + ITEM_PREFIX
						+ "_" + wi.getId());
				// eventBus.fireEvent(new ListUpdateEvent(getWishList()));
				firedUpdate();
			}
		}
	}

	public void saveWishItem(WishItem wi) {
		// TO-DO
		if (localStore != null) {
			if (wi.getId() == null) {
				String t_id = getFreeID();
				if (!(t_id.equals(""))) {
					wi.setId(t_id);

					localStore.setItem(APP_STORAGE_PREFIX + "." + ITEM_PREFIX
							+ "_" + t_id, wi.toJSON());
				}
			} else {
				localStore.setItem(APP_STORAGE_PREFIX + "." + ITEM_PREFIX + "_"
						+ wi.getId(), wi.toJSON());
			}
		}
		// eventBus.fireEvent(new ListUpdateEvent(getWishList()));
		firedUpdate();
	}

	private String getFreeID() {
		String res = "";
		if (localStore != null) {
			Date date = new Date();
			res = "" + date.getTime();
			while (localStore.getItem(APP_STORAGE_PREFIX + "." + ITEM_PREFIX
					+ "_" + res) != null) {
				res = "" + date.getTime();
			}
		}
		return res;
	}

	public void firedUpdate() {
		eventBus.fireEvent(new ListUpdateEvent(getWishList()));
	}

	public WishList getWishList() {
		wishList.setList(readWishList());
		return wishList;
	}

	public LinkedList<WishItem> readWishList() {
		LinkedList<WishItem> list = new LinkedList<WishItem>();
		if (localStore != null) {
			for (int i = localStore.getLength() - 1; i > 0; i--) {
				String key = localStore.key(i);
				if (key.startsWith(APP_STORAGE_PREFIX + "." + ITEM_PREFIX)) {
					JSONValue value = JSONParser.parseStrict(localStore
							.getItem(key));
					JSONObject wishObject = value.isObject();

					WishItem m = new WishItem();
					m.setId(wishObject.get("id").isString().stringValue());
					m.setDescription(wishObject.get("description").isString()
							.stringValue());
					m.setNotes(wishObject.get("notes").isString().stringValue());
					m.setReason(wishObject.get("reason").isString()
							.stringValue());
					m.setWhere(wishObject.get("where").isString().stringValue());
					m.setWhereURL(wishObject.get("where_url").isString()
							.stringValue());
					m.setPrice(new Price(wishObject.get("price").isString()
							.stringValue(), Currency.RUB));
					m.setPicture(new Picture(wishObject.get("picture_url")
							.isString().stringValue()));
					m.getPicture().setData(
							wishObject.get("picture_data").isString()
									.stringValue());

					list.add(m);
				}
			}
		}
		return list;
	}
}
