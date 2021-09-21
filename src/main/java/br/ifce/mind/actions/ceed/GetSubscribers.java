/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class GetSubscribers extends AbstractAction {
    public void act(Object oEvent, Object callback) { 
        String eventName = (String) oEvent;
        Agent agent = this.getAgent();
        agent.see("get", "subscribers", new AbstractAction() {
            public void act(Object oSubscribers, Object c) {
                Map<String, List<Agent>> subscribers = (HashMap<String, List<Agent>>) oSubscribers;
                if (subscribers == null) {
                    subscribers = new HashMap<String, List<Agent>>();
                    agent.see("set", new Object[] {"subscribers", subscribers});
                }
                if (eventName  == null) {
                    callback(callback, subscribers);
                }
                else {
                    List<Agent> agents = subscribers.get(eventName);
                    if (agents == null) {
                        agents = new ArrayList<Agent>();
                        subscribers.put(eventName, agents);
                    }
                    callback(callback, agents);
                }
            }
        });
    }
}
