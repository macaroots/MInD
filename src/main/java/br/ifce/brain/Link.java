/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import org.json.JSONObject;

public class Link {

	private int id;
	private Symbol a, b, r;

	public Link() {
	}
	public Link(Symbol r) {
		setR(r);
	}
	public Link(Symbol a, Symbol r) {
		setA(a);
		setR(r);
	}
	public Link(Symbol a, Symbol r, Symbol b) {
		setA(a);
		setR(r);
		setB(b);
	}
	
	public Symbol getA() {
		return a;
	}
	public void setA(Symbol a) {
		this.a = a;
	}
	public Symbol getB() {
		return b;
	}
	public void setB(Symbol b) {
		this.b = b;
	}
	public Symbol getR() {
		return r;
	}
	public void setR(Symbol r) {
		this.r = r;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String toString() {
		return getA() + " (" + getR() + ") " + getB();
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		if (this.getA() != null) {
			json.put("a", this.getA().toJSONObject());
		}
		if (this.getR() != null) {
			json.put("r", this.getR().toJSONObject());
		}
		if (this.getB() != null) {
			json.put("b", this.getB().toJSONObject());
		}
		return json;
	}
	public String toJSON() {
		return this.toJSONObject().toString();
	}
	public Link fromJSON(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		return this.fromJSON(json);
	}
	public Link fromJSON(JSONObject json) {
		this.setA(new Symbol().fromJSON(json.getJSONObject("a")));
		this.setR(new Symbol().fromJSON(json.getJSONObject("r")));
		this.setB(new Symbol().fromJSON(json.getJSONObject("b")));
		return this;
	}
	
}
