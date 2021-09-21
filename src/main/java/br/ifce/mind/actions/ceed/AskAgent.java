/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class AskAgent extends AbstractAction {

	Agent helper;
	public AskAgent(Agent helper) {
		this.helper = helper;
	}
	/**
	 * [Agent wantsToKnow, String concept]
	 */
	@Override
	public void act(Object args, Object callback) {
		Agent agent = this.getAgent();
		this.helper.see("askFor", new Object[] {agent, args}, callback);
	}
	
}
