/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.List;

import br.ifce.brain.Brain;
import br.ifce.brain.Symbol;
import br.ifce.mind.Agent;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class Write extends AbstractAction {

	public void act(Object object, Object callback) {
		Object [] arg;
		try {
			arg = (Object []) object;
		}
		catch (ClassCastException e) {
			Symbol concept = (Symbol) object;
			arg = new Object [] {concept.getType(), concept.getInfo()};
			this.getAgent().see("warning", "old_write: " + concept.getType());
		}
		Object [] args = arg;
		String type = (String) args[0];
		
		
		Agent agent = this.getAgent();
		agent.see("getLibraries", null, new AbstractAction() {
		//agent.see("get", "library", new AbstractAction() {
			public void act(Object object, Object callback2) {
				//Brain brain = (Brain) object;
				List<Brain> brains = (List<Brain>) object;
				Brain brain = brains.get(brains.size() - 1);
				String bookname;
				if (type.indexOf(".") == -1) {
					bookname = ((NaiveMind) agent.getMind()).getName();
				}
				else {
					String [] types = type.split("\\.(?=[^\\.]*$)");
					bookname = types[0];
					args[0] = types[1];
				}
				
				Action writer = ((NaiveMind) agent.getMind()).getWrite();
				//for (Brain brain: brains)
					agent.see(writer, new Object [] {brain, bookname, args}, callback);
				
			}
		});
	}

}
