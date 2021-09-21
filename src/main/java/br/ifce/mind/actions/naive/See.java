/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import java.util.List;

import br.ifce.brain.Symbol;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class See extends AbstractAction {
	public void act(Object perception, Object callback) {
		try {
			act((Symbol) perception, callback);
		}
		catch (ClassCastException e) {
			act(new Symbol(perception), callback);
		} 
	}
	public void act(Symbol perception, Object callback) {
// TODO Será que deveria anotar tudo o que foi visto?
// TODO Não estamos fazendo nada com isso, então esse comportamento pode ser alterado depois.
		NaiveMind agent = (NaiveMind) this.getAgent().getMind();
		
		Object type = perception.getType();
		
		try {
			agent.act((Action) type, perception.getInfo(), callback);
		} catch (ClassCastException e) {
			// procura uma ação
			agent.get(type, new AbstractAction() {
				public void act(Object object, Object callback2) {
					List<Object> actions = (List<Object>) object;
					if (!actions.isEmpty()) {
						boolean executed = false;
						Object meaning = null;
						for (int i = actions.size() - 1; i >= 0; i--) {
							try {
                                meaning = actions.get(i);
								Action action = (Action) meaning;
								agent.act(action, perception.getInfo(), callback);
								executed = true;
								break;
							}
							catch (Exception e) {
								// TODO deve ser passado para agent.see("error")?
								System.err.println("Error! " + e.getStackTrace()[0].getFileName() + " line " + e.getStackTrace()[0].getLineNumber() + ": " + e.getMessage());
								
								System.err.println("Couldn't execute (" + perception + ") meaning:");
								System.err.println(meaning);
								System.err.println("Searching another action... ");
							}
						}
						if (!executed && type != null && !type.equals("notAction") && !type.equals("dontKnow")) {
							agent.see(new Symbol("notAction", new Object[] {perception, meaning}), callback);
						}
					}
					else {
						/*
				   se não, procura o que fazer quando não sabe, que deve
				   de alguma forma tentar aprender.
				   Bem, isso pode ser decidido depois.
						 */
						if (type != null && !type.equals("dontKnow")) {
							agent.see(new Symbol("dontKnow", perception), callback);
						}
						else {
							this.doCallback(callback, null);
						}
					}
				}
			});
		}
	}

}
