/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.NaiveMind;
import br.ifce.brain.Brain;
import br.ifce.brain.Link;

import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class Help extends AbstractAction {
	public void act(Object args, Object callback) {
        Map<String, Map> knowledge = new HashMap<String, Map>();
        {
            Map<String, Set<String>> agents = new HashMap<String, Set<String>>();
            Brain brain = ((NaiveMind) this.getAgent().getMind()).getBrain();
            List<Link> links = brain.reason(null);
            for (Link link: links) {
                Set<String> actions = agents.get(link.getA().getInfo().toString());
                if (actions == null) {
                    actions = new LinkedHashSet<String>();
                    agents.put(link.getA().getInfo().toString(), actions);
                }
                actions.add(link.getR().getInfo().toString());
            }
            knowledge.put("knows", agents);
        }
        
        
	
        this.getAgent().see("getLibraries", new AbstractAction() {
            public void act(Object oBrains, Object c) {
                Map<String, Set<String>> agents = new HashMap<String, Set<String>>();
                List<Brain> brains = (List<Brain>) oBrains;
                for (Brain brain: brains) {
                    List<Link> links = brain.reason(null);
                    for (Link link: links) {
                        Set<String> actions = agents.get(link.getA().getInfo().toString());
                        if (actions == null) {
                            actions = new LinkedHashSet<String>();
                            agents.put(link.getA().getInfo().toString(), actions);
                        }
                        actions.add(link.getR().getInfo().toString());
                    }
                }
                knowledge.put("learnable", agents);
                
                callback(callback, knowledge);
            }
        });
        
	}
}
