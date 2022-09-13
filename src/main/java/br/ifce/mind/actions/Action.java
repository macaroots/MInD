/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions;

import br.ifce.mind.Agent;

public interface Action<T> {

	/**
	 * Acts following arguments
	 * @param args
	 * @return
	 */
	void act(T args, Object callback);
	void setAgent(Agent agent);
	Agent getAgent();
	public void callback(Object callback, Object result);
	
}
