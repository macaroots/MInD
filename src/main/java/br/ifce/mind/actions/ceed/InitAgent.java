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
import br.ifce.brain.Brain;
import br.ifce.mind.actions.AbstractAction;

import java.util.List;

public class InitAgent extends AbstractAction<Agent> {

	@Override
	public void act(Agent agent, Object callback) {
        Agent ceed = this.getAgent();
        agent.see("set", new Object[] {"parent", ceed});        
        
		ceed.see("getLibraries", null, new AbstractAction<List<Brain>>() {
			public void act(List<Brain> libraries, Object callback2) {
                for (Brain library: libraries) {
                    agent.see("addLibrary", library);
                }
            }
        });
        
        callback(callback, null);
	}

}
