/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class Act extends AbstractAction {

	public void act(Object object, Object callback) {
		Action action = null;
		Object target = null;
		Agent agent = this.getAgent();
		try {
			Object [] args = (Object []) object;
			action = (Action) args[0];
			action.setAgent(agent);
			target = args[1];
			action.act(target, callback);
		}
		catch (RuntimeException e) {
			String message = ("Runtime error! \"" + agent + "\"\n\t trying \"" + action + "\" - \"" + target + "\":\n\t " + e.getMessage());
			try { this.getAgent().see("error", new Object [] {e, message}); } finally {}
		}
		catch (Exception e) {
			String message = ("Error! \"" + agent + "\"\n\t trying \"" + action + "\" - \"" + target + "\":\n\t " + e.getMessage());
			try { this.getAgent().see("error", new Object [] {e, message}); } finally {}
		}
	}

}
