package com.ds.wishlist.client;


import com.ds.wishlist.client.events.ListUpdateEvent;
import com.ds.wishlist.client.events.handlers.ListUpdatedHandler;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiHandler;

public class WishListWidget extends Composite {
	private EventBus eventBus;
	private WishList wishList;

	private boolean firstInit = true;
	private boolean flagEditFirst = false;
	private WishItem newItem;
	private WishItemWidget newItemWidget;

	@UiField
	VerticalPanel wishListPanel;
	@UiField
	FlowPanel newItemPanel;
	@UiField
	HTMLPanel startMesagePanel;
	@UiField
	Button addButton;

	private static WishListWidgetUiBinder uiBinder = GWT
			.create(WishListWidgetUiBinder.class);

	interface WishListWidgetUiBinder extends UiBinder<Widget, WishListWidget> {
	}

	@UiHandler("addButton")
	void addButton_onClick(ClickEvent e) {
		showAddWidget();
	}

	// public WishListWidget(EventBus ev, WishList wl) {
	public WishListWidget(EventBus ev) {

		initWidget(uiBinder.createAndBindUi(this));
		// wishList = wl;

		eventBus = ev;
		wishList = new WishList();

		newItem = new WishItem();
		newItemWidget = new WishItemWidget(eventBus, newItem, null) {
			@Override
			public void done_button_override() {
				hideAddWidget();
			}
			@Override
			public void cancel_button_override() {
				hideAddWidget();
			}
		};

		newItemPanel.add(newItemWidget);
		newItemPanel.addStyleName("hidden");

		eventBus.addHandler(ListUpdateEvent.TYPE, new ListUpdatedHandler() {
			public void onEvent(ListUpdateEvent event) {
				refreshList(event.getWishList());
			}
		});

		//refreshList();
	}
	
	public void editFirst() {
		this.flagEditFirst = true;
	};

	private void showAddWidget() {
		newItem.clear();
		newItemWidget.update(newItem);
		newItemWidget.activateEditMode();
		addButton.addStyleName("hidden");
		newItemPanel.removeStyleName("hidden");
	}

	private void hideAddWidget() {
		addButton.removeStyleName("hidden");
		newItemPanel.addStyleName("hidden");
	}

	public WishList getWhisList() {
		return wishList;
	}

	public void refreshList(WishList wl) {
		
		
		
		if (wl.getSize() == 0) {
			startMesagePanel.removeStyleName("hidden");
		} else {
			startMesagePanel.addStyleName("hidden");
		}
		
		wishList = wl;
		int childs_count = wishListPanel.getWidgetCount();
		int whishList_size = wishList.getSize();
		if (whishList_size<childs_count) {
			for (int rw=whishList_size; rw<childs_count; rw++) {
				wishListPanel.remove(rw);
			}
		}
		if (childs_count > 0) {
			for (int rw = 0; (rw < whishList_size) && (rw < childs_count); rw++) {
				// rewrite an existed item
				WishItemWidget itm = (WishItemWidget) wishListPanel
						.getWidget(rw);
				itm.update(wishList.getItem(rw));
			}
		}
		for (int rw = childs_count; rw < whishList_size; rw++) {			
			wishListPanel.add(new WishItemWidget(eventBus,
					wishList.getItem(rw), this));
		}
		
		if ((firstInit) && (flagEditFirst)) {
			if (wishListPanel.getWidgetCount()>0) {
				((WishItemWidget) wishListPanel.getWidget(0)).activateEditMode();
			}
			flagEditFirst = false;
		}
		firstInit = false;
	}

	private void saveWishItem(WishItem wi) {
		String id = wi.getId();
		if (id != null) {

		} else {

		}
	}
}
