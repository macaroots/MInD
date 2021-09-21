/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class NewAgent extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		String agentName = (String) args;
		Agent agent = new Ceed(agentName);
        Agent ceed = Ceed.getInstance();
        agent.see("set", new Object[] {"parent", ceed});
        ceed.see("set", new Object[] {"agent_" + agentName, agent});
        ceed.see("teach", agent);
        ceed.see("registerAgent", agent);
        ceed.see("initAgent", agent, new AbstractAction() {
            public void act(Object a, Object c) {
                callback(callback, agent);
            }
        });
        ceed.see("notify", new Object[] {"newAgent", agent});
	}

}
