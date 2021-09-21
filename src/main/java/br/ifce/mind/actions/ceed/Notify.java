/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.List;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class Notify extends AbstractAction {
    public void act(Object oArgs, Object callback) { 
        Object[] args = (Object[]) oArgs;
        String eventName = (String) args[0];
        Object event = args[1];
        
        String capName = eventName.substring(0, 1).toUpperCase() + eventName.substring(1);
  
        Agent agent = this.getAgent();
        agent.see("getSubscribers", eventName, new AbstractAction() {
            public void act(Object oSubscribers, Object c) {
                List<Agent> subscribers = (List<Agent>) oSubscribers;
                for (Agent agent: subscribers) {
                    agent.see("on" + capName, event);
                }
            }
        });
        callback(callback, null);
    }
}
