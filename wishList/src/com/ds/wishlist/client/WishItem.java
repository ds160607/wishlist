package com.ds.wishlist.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;


public class WishItem {

	private enum Status {
		A, D, R, F
	}; // A-active, D-done, R-removed, F-first null item

	private String id = null;
	private String description = "";
	private Price price = new Price("0.0", Currency.RUB);
	private Picture picture = new Picture("");
	private String reason = "";
	private String where = "";
	private String where_url = "";
	private String notes = "";

	private Status status = Status.A;

	public WishItem() {
	}

	public void clear() {
		id = null;
		description = "";
		price = new Price("0.0", Currency.RUB);
		picture = new Picture("");
		reason = "";
		where = "";
		where_url = "";
		notes = "";
	}

	public void setId(String i) {
		id = i;
	}

	public void setDescription(String des) {
		description = des;
	}

	public void setPrice(Price p) {
		price = p;
	}

	public void setPicture(Picture pc) {
		picture = pc;
	}

	public void setReason(String s) {
		reason = s;
	}

	public void setWhere(String s) {
		where = s;
	}

	public void setWhereURL(String s) {
		where_url = s;
	}

	public void setNotes(String s) {
		notes = s;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Price getPrice() {
		return price;
	}

	public Picture getPicture() {
		return picture;
	}

	public String getReason() {
		return reason;
	}

	public String getWhere() {
		return where;
	}

	public String getWhereURL() {
		return where_url;
	}

	public String getNotes() {
		return notes;
	}

	public String toJSON(){
		
		/*
		 * String s = "{}";
		 * m.setId (wishObject.get("id").isString().stringValue());
		 * m.setDescription
		 * (wishObject.get("descripton").isString().stringValue()); m.setNotes
		 * (wishObject.get("notes").isString().stringValue()); m.setReason
		 * (wishObject.get("reason").isString().stringValue()); m.setWhere
		 * (wishObject.get("where").isString().stringValue());
		 */

		JSONObject res = new JSONObject();
		
		res.put("id", new JSONString(id));
		res.put("description", new JSONString(description));
		res.put("notes", new JSONString(notes));
		res.put("reason", new JSONString(reason));
		res.put("where", new JSONString(where));
		res.put("where_url", new JSONString(where_url));
		res.put("price", new JSONString(price.getPrice()));
		res.put("picture_url", new JSONString(picture.getURL()));
		res.put("picture_data", new JSONString(picture.getData()));

		/*
		 * s=s+ "\"id\":\"" +id +"\"," + "\"description\":\""+description +"\","
		 * + "\"notes\":\"" +notes +"\"," + "\"reason\":\"" +reason +"\"," +
		 * "\"where\":\"" +where +"\"," + "\"where_url\":\"" +where_url +"\"," +
		 * "\"price\":\"" +price.getPrice() +"\"," +
		 * "\"picture_url\":\""+picture.getURL() +"\"," +
		 * "\"picture_data\":\""+picture.getData() +"\"" + "}";
		 */
		return res.toString();

	}

}
