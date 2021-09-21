/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.HashMap;
import java.util.List;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class GetAgents extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		Agent agent = Ceed.getInstance();
		agent.see("getAll", "agent", new AbstractAction() {
			@Override
			public void act(Object allAgents, Object callback2) {
				callback(callback, allAgents);
            }
        });
	}

}
