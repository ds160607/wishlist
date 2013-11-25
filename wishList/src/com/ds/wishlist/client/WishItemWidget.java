package com.ds.wishlist.client;

import com.ds.wishlist.client.events.WishItemRemoveEvent;
import com.ds.wishlist.client.events.WishItemSaveItemEvent;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class WishItemWidget extends Composite {
	private String mode = "read";
	private static WishItemWidgetUiBinder uiBinder = GWT
			.create(WishItemWidgetUiBinder.class);

	interface WishItemWidgetUiBinder extends UiBinder<Widget, WishItemWidget> {
	}

	private WishItem wishItem;
	private WishListWidget parent;
	private EventBus eventBus;
	

	@UiField
	Image photo;

	@UiField
	Label description;
	@UiField
	Label description_label;
	@UiField
	TextBox description_edit;

	@UiField
	InlineLabel price;
	@UiField
	InlineLabel price_read_label;
	@UiField
	Label price_label;
	@UiField
	TextBox price_edit;
	@UiField
	FlowPanel price_read_container;

	@UiField
	InlineLabel reason;
	@UiField
	InlineLabel reason_read_label;
	@UiField
	Label reason_label;
	@UiField
	TextBox reason_edit;
	@UiField
	FlowPanel reason_read_container;

	@UiField
	Anchor where_url;
	@UiField
	Label where_url_label;
	@UiField
	TextBox where_url_edit;

	@UiField
	InlineLabel where;
	@UiField
	InlineLabel where_read_label;
	@UiField
	Label where_label;
	@UiField
	TextBox where_edit;
	@UiField
	FlowPanel where_read_container;

	@UiField
	Label notes;
	@UiField
	Label notes_label;
	@UiField
	TextBox notes_edit;

	@UiField
	Button edit_button;
	@UiField
	Button remove_button;
	@UiField
	Button done_button;
	@UiField
	Button cancel_button;
	
	/**
	 * Creates a WishItem widget that display a data form WishItem object.
	 * 
	 * @param EventBus - a share event bus 
	 * @param WishItem - an object which represents a data for displaying
	 * @param WishListWidget - a parent WhishList container
	 */
	public WishItemWidget(EventBus ev, WishItem wi, WishListWidget p) {
		eventBus = ev;
		wishItem = wi;
		parent = p;
		initWidget(uiBinder.createAndBindUi(this));

		// description_edit.getElement().setAttribute("placeholder",
		// "описание ...");
		// notes_edit.getElement().setAttribute("placeholder",
		// "примечание ...");

		done_button.addStyleName("hidden");
		cancel_button.addStyleName("hidden");
		activateReadMode();
		update();
	}

	@UiHandler("edit_button")
	void edit_button_onClick(ClickEvent e) {
		activateEditMode();

	}

	@UiHandler("where_url")
	void where_url_onClick(ClickEvent e) {
		openLink(wishItem.getWhereURL());
	}

	public static native void openLink(String link)/*-{		
		chrome.extension.sendMessage({
			'messageType' : 'openLink',
			'link' : link
		}, function(response) {
		});
	}-*/;

	@UiHandler("remove_button")
	public void remove_button_onClick(ClickEvent e) {
		eventBus.fireEvent(new WishItemRemoveEvent(wishItem));
	}

	@UiHandler("done_button")
	void done_button_onClick(ClickEvent e) {
		wishItem.setDescription(description_edit.getValue());
		wishItem.setNotes(notes_edit.getValue());
		wishItem.setReason(reason_edit.getValue());
		wishItem.setPrice(new Price(price_edit.getValue(), Currency.RUB));
		wishItem.setWhere(where_edit.getValue());
		mode = "read";
		saveItem();
		// activateReadMode();
		done_button_override();
	}

	public void done_button_override() {

	}

	@UiHandler("cancel_button")
	public void cancel_button_onClick(ClickEvent e) {
		activateReadMode();
		cancel_button_override();
	}

	public void cancel_button_override() {

	}

	public void saveItem() {
		eventBus.fireEvent(new WishItemSaveItemEvent(wishItem));
	}

	public void activateEditMode() {

		mode = "edit";

		edit_button.addStyleName("hidden");
		remove_button.addStyleName("hidden");
		done_button.removeStyleName("hidden");
		cancel_button.removeStyleName("hidden");

		description.addStyleName("hidden");
		description_edit.setValue(wishItem.getDescription());
		description_label.removeStyleName("hidden");
		description_edit.removeStyleName("hidden");

		// price.addStyleName("hidden");
		price_read_container.addStyleName("hidden");
		price_edit.setValue("" + wishItem.getPrice().getPrice());
		price_label.removeStyleName("hidden");
		price_edit.removeStyleName("hidden");

		// reason.addStyleName("hidden");
		reason_read_container.addStyleName("hidden");
		reason_edit.setValue(wishItem.getReason());
		reason_label.removeStyleName("hidden");
		reason_edit.removeStyleName("hidden");

		where_url.addStyleName("hidden");
		where_url_edit.setValue(wishItem.getWhereURL());
		where_url_label.removeStyleName("hidden");
		where_url_edit.removeStyleName("hidden");

		// where.addStyleName("hidden");
		where_read_container.addStyleName("hidden");
		where_edit.setValue(wishItem.getWhere());
		where_label.removeStyleName("hidden");
		where_edit.removeStyleName("hidden");

		notes.addStyleName("hidden");
		notes_edit.setValue(wishItem.getNotes());
		notes_edit.removeStyleName("hidden");
		notes_label.removeStyleName("hidden");

		description_edit.setFocus(true);

	}

	public void activateReadMode() {

		mode = "read";

		edit_button.removeStyleName("hidden");
		remove_button.removeStyleName("hidden");
		done_button.addStyleName("hidden");
		cancel_button.addStyleName("hidden");

		description_edit.addStyleName("hidden");
		description_label.addStyleName("hidden");
		description.removeStyleName("hidden");

		// price.removeStyleName("hidden");
		if (wishItem.getPrice().getPrice().equals("")) {
			price_read_label.addStyleName("hidden");
		} else {
			price_read_label.removeStyleName("hidden");
		}
		price_read_container.removeStyleName("hidden");
		price_edit.addStyleName("hidden");
		price_label.addStyleName("hidden");

		// reason.removeStyleName("hidden");
		if (wishItem.getReason().equals("")) {
			reason_read_label.addStyleName("hidden");
		} else {
			reason_read_label.removeStyleName("hidden");
		}
		reason_read_container.removeStyleName("hidden");
		reason_edit.addStyleName("hidden");
		reason_label.addStyleName("hidden");

		where_url_edit.addStyleName("hidden");
		where_url_label.addStyleName("hidden");
		where_url.removeStyleName("hidden");

		// where.removeStyleName("hidden");
		if (wishItem.getWhere().equals("")) {
			where_read_label.addStyleName("hidden");
		} else {
			where_read_label.removeStyleName("hidden");
		}
		where_read_container.removeStyleName("hidden");
		where_edit.addStyleName("hidden");
		where_label.addStyleName("hidden");

		notes_edit.addStyleName("hidden");
		notes_label.addStyleName("hidden");
		notes.removeStyleName("hidden");
	}

	public void update() {
		update(wishItem);
	}

	public void update(WishItem wi) {
		wishItem = wi;

		// item_id.setText(wi.getId());

		photo.setUrl(wi.getPicture().getData());

		description.setText(wi.getDescription());
		reason.setText(wi.getReason());
		price.setText("" + wi.getPrice().getPrice());
		// + " " + wi.getPrice().getCurrency().getDisplay());

		String t = wi.getWhereURL();
		if (t.length() > 60) {
			t = t.substring(0, 57) + "...";
		}
		where_url.setText(t);
		//where_url.setHref(wi.getWhereURL());

		where.setText(wi.getWhere());
		notes.setText(wi.getNotes());
		if (mode.equals("read")) {
			activateReadMode();
		} else {
			activateEditMode();
		}
	}

}
