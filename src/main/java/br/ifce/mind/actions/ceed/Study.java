/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.List;

import br.ifce.brain.Symbol;
import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class Study extends AbstractAction {

	public void act(Object object, Object callback) {			
		Symbol perception;
		try {
			perception = (Symbol) object;
		} catch (ClassCastException e) {
			perception = new Symbol((String) object, null);
		}
		Object concept = perception.getType();
		
		Agent agent = this.getAgent();
		
		// tenta ler no banco de dados
		agent.see("read", concept, new AbstractAction() {
			public void act(Object object, Object callback2) {
				List<Symbol> representations = (List<Symbol>) object;
				if (representations != null && !representations.isEmpty()) {
					Symbol representation = representations.get(representations.size() - 1);
					representation.setId(0);
					agent.see("understand", new Object [] {concept, representation}, callback);
				}
				else {
					doCallback(callback, false);
				}
			}
		});
	}

}
