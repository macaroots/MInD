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

public class SetLibrary extends AbstractAction {

	public void act(Object target, Object callback) {
		Agent agent = this.getAgent();
		agent.see("set", new Object [] {"library", target});
	}

}
