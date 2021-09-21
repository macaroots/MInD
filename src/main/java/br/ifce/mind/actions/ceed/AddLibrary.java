/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.brain.Brain;
import br.ifce.brain.MySQLBrain;
import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class AddLibrary extends AbstractAction {

	public void act(Object brain, Object callback) {
		Agent agent = this.getAgent();
        agent.see("set", new Object [] {"library", brain});
        doCallback(callback, brain);
	}

}
