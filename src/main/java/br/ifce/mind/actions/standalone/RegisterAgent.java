/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class RegisterAgent extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		Agent agent = (Agent) args;
		Agent ceed = Ceed.getInstance();
        ceed.see("set", new Object[] {"agent", agent});
		agent.see("getNames", null, new AbstractAction() {
            @Override
            public void act(Object args, Object callback2) {
                List<String> names = (List<String>) args;
                for (String name: names) {
                    ceed.see("set", new Object[] {"agent_" + names, agent});
                }
            }
        });
		callback(callback, null);
	}
}
