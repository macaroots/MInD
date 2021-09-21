/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.brain.Symbol;
import br.ifce.mind.Mind;
import br.ifce.mind.actions.AbstractAction;

public class Understand extends AbstractAction {

	public void act(Object object, Object callback) {
		Object [] args;
		try {
			args = (Object []) object;
		}
		catch (ClassCastException e) {
			Symbol concept = (Symbol) object;
			args = new Object [] {concept.getType(), concept.getInfo()};
			this.getAgent().see("warning", "old_understand: " + concept.getType());
		}
		String name;
		Object answer;
		name = (String) args[0];
		answer = args[1];
		
		// TODO acho que pode usar Agent
		Mind agent = this.getAgent().getMind();
		
		// tenta interpretar o que o outro falou
		agent.see(answer, new AbstractAction() {
			public void act(Object interpretedMeaning, Object callback2) {
				boolean learned = true;
				if (interpretedMeaning == null) {
					// senão, anota sem entender
					interpretedMeaning = answer;
					learned = false;
				}
				
				agent.see(new Symbol("set", new Object[] {name, interpretedMeaning}));
				
				doCallback(callback, learned);
			}
		});
	}

}
