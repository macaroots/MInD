/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import java.util.HashMap;
import java.util.Map;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.EmptyAction;

import br.ifce.mind.actions.gui.GUI;
import br.ifce.mind.actions.gui.AskClick;
import br.ifce.mind.actions.gui.SeeClick;
import br.ifce.mind.actions.gui.ListAgents;

/*
Acho que o certo seria escrever tudo isso em uma biblioteca
*/
public class TeachStandAlone extends AbstractAction {

    private static Map<String, Object> skills;

    public static Map<String, Object> getSkills() {
		if (skills == null) {
            skills = new HashMap<String, Object>();
            skills.put("main", new Main());
            skills.put("console", new Console());
            skills.put("listen", new Listen());
            skills.put("callAgent", new CallAgent());
            skills.put("registerAgent", new RegisterAgent());
            skills.put("config", new DefaultConfig());
            skills.put("initAgent", new EmptyAction());
            
            skills.put("gui", new GUI());
            skills.put("seeClick", new SeeClick());
            skills.put("askClick", new AskClick());
            skills.put("listAgents", new ListAgents());
        }
		return skills;
	}
	public void act(Object args, Object callback) { 
		Agent student = (Agent) args;
		
        this.getSkills().forEach((key, value) -> {
            student.see("set", new Object[] {key, value});
        });
        doCallback(callback, true);
	}

}
