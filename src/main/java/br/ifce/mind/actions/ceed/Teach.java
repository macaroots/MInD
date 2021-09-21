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

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class Teach extends AbstractAction {
    private static Map<String, Object> skills;
	public static Map<String, Object> getSkills() {
		if (skills == null) {
            skills = new HashMap<String, Object>();
            skills.put("dontKnow", new DontKnow());
            skills.put("study", new Study());
            skills.put("getLibraries", new GetLibraries());
            skills.put("addLibrary", new AddLibrary());
            skills.put("setLibrary", new SetLibrary());
            skills.put("read", new Read());
            skills.put("understand", new Understand());
            skills.put("ask", new AskAgent(Ceed.getInstance()));
            skills.put("askFor", new AskFor());
            skills.put("hear", new Hear());
            skills.put("write", new Write());
            
            skills.put("require", new Require());
            skills.put("requirePassword", new RequirePassword());
            skills.put("java", new Java());
            skills.put("object", new JavaObject());
            skills.put("notAction", new NotAction());
            
            skills.put("getAgent", new GetAgent());
            skills.put("getAgents", new GetAgents());
            skills.put("newAgent", new NewAgent());
            
            skills.put("notify", new Notify());
            skills.put("subscribe", new Subscribe());
            skills.put("getSubscribers", new GetSubscribers());
            
            skills.put("configMysql", new ConfigMysql());
            skills.put("help", new Help());
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
