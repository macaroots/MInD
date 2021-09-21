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

public class DontKnow extends AbstractAction {
	public void act(Object object, Object callback) {
		Symbol perception = (Symbol) object;
		Object concept = perception.getType();
		
		Agent agent = this.getAgent();

		agent.see("getAll", "unknowns", new AbstractAction() {
			public void act(Object rGet, Object callback2) {
				List<Object> unknowns = (List<Object>) rGet;
				if (unknowns.contains(concept)) {
					System.out.println(agent + " - Don't know '" + concept + "': ignore");
					doCallback(callback, null);
					return;
				}
				agent.see("set", new Object [] {"unknowns", concept});

				System.out.println(agent + " - Don't know '" + concept + "': try to find out");
				
				// tenta ler no banco de dados
				agent.see("study", perception, new AbstractAction() {
					public void act(Object rStudy, Object callback2) {
						boolean learned = (Boolean) rStudy;
						if (learned) {
							agent.see(perception, callback);
						}
						else {
							// pergunta pro programador
							if (!concept.equals("ask")) {
								agent.see("ask", perception, new AbstractAction() {
									public void act(Object r, Object callback2) {
										boolean learned = (r != null) ? (Boolean) r: false;
										if (learned) {
											agent.see(perception, callback);
										}
										else {
											doCallback(callback, null);
										}
									}
								});
							}
						}
					}
				});
			}
		});
	}
	
}
