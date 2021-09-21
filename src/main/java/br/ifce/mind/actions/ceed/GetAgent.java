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
import java.util.Map;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class GetAgent extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		String agentName = (String) args;
		Agent ceed = Ceed.getInstance();
		ceed.see("get", "agent_" + agentName, new AbstractAction() {
			@Override
			public void act(Object args2, Object callback2) {
				Agent agent = (Agent) args2;
				if (agent != null) {
                    callback(callback, agent);
				}
				else {
					Ceed.newAgent(agentName, callback);
				}
			}
		});
	}

}
