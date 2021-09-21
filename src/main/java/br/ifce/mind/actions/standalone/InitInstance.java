/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.Agent;
import br.ifce.mind.actions.ceed.Teach;
import br.ifce.mind.actions.standalone.TeachStandAlone;

public class InitInstance extends AbstractAction {
    public void act(Object args, Object callback) {
        Agent agent = this.getAgent();
        agent.see("set", new Object[] {"teach", new Teach()});
        agent.see("set", new Object[] {"teachStandAlone", new TeachStandAlone()});
        agent.see("teach", agent);
        agent.see("teachStandAlone", agent);
        agent.see("registerAgent", agent);
        agent.see("config", new AbstractAction() {
            public void act(Object a, Object c) {
                agent.see("initAgent", agent);
                callback(callback, true);
            }
        });
    }
}
