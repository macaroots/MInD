/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import java.util.Date;

import org.json.JSONObject;

public class Symbol {

	private int id;
	private Object type;
	private Object info;
	private Date date;

	public Symbol() {
        setDate(new Date(System.currentTimeMillis()));
	}
	public Symbol(int id) {
        this();
		setId(id);
	}
	public Symbol(Object info) {
        this();
		if (info != null) {
			setType(info.getClass().getName());
			setInfo(info);
		}
	}
	public Symbol(Object type, Object info) {
        this();
		setType(type);
		setInfo(info);
	}
	public Symbol(int id, String type) {
        this();
		setId(id);
		setType(type);
	}
	public Symbol(int id, String type, Object info) {
        this();
		setId(id);
		setType(type);
		setInfo(info);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
	public Object getInfo() {
		return info;
	}
	public void setInfo(Object info) {
		this.info = info;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String toString() {
		return getId() + " | " + getType() + " | " + getInfo() + " | " + getDate();
	}
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("type", this.getType());
		json.put("info", this.getInfo());
		json.put("date", this.getDate());
		return json;
	}
	public String toJSON() {
		return toJSONObject().toString();
	}
	public Symbol fromJSON(JSONObject json) {
		this.setId(json.getInt("id"));
		this.setType(json.getString("type"));
		this.setInfo(json.get("info"));
		return this;
	}
	public Symbol fromJSON(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		return this.fromJSON(json);
	}
}
