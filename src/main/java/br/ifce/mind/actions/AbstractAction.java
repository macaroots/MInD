/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions;

import br.ifce.mind.Agent;

public abstract class AbstractAction<T> implements Action<T> {

	private Agent agent;
	
	public AbstractAction() {
	}
	public AbstractAction(Agent agent) {
		setAgent(agent);
	}
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Action init(Agent agent) {
		this.setAgent(agent);
		return this;
	}
	public void doCallback(Object callback, Object result) {
		callback(callback, result);
	}
	public void callback(Object callback, Object result) {
		try {
			if (callback != null) {
				this.getAgent().see(callback, result);
			}
		} catch (ClassCastException e) {
			this.getAgent().see(callback, result);
		} catch (Exception e) {
			this.getAgent().see("error", new Object [] {e, "Error on callback!"});
		}
	}
	
}
