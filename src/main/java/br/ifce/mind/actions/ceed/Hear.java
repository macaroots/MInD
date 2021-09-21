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

/**
 * Deve anotar a resposta relacionada a quem falou ou de onde leu
 * Depois deve aprender a interpretação!
 */
public class Hear extends AbstractAction {

	public void act(Object args, Object callback) {
		Agent agent = this.getAgent();
		agent.see("write", args);
		agent.see("understand", args, callback);
	}

}
