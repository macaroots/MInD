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
import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class GetLibraries extends AbstractAction {

	/**
	 * object pode ser os dados do host
	 */
	public void act(Object object, Object callback) {
		Agent agent = this.getAgent();
		agent.see("getAll", "library", new AbstractAction() {
			public void act(Object object, Object callback2) {
				List<Brain> brains = (List<Brain>) object;
//System.out.println("GET_LIBRARIES: " + brains);
				if (brains.isEmpty()) {
					agent.see("get", "parent", new AbstractAction() {
						public void act(Object object, Object callback2) {
							Agent parent = (Agent) object;
							if (parent != null) {
								parent.see("get", "library", new AbstractAction() {
									public void act(Object library, Object callback2) {
										if (library != null) {
											agent.see("set", new Object [] {"library", library});
											brains.add((Brain) library);
										}
//System.out.println("GET_LIBRARIES_PARENT: " + brains);
                                        doCallback(callback, brains);
									}
								});
							}
							else {
                                doCallback(callback, brains);
							}
						}
					});
				}
				else {
					doCallback(callback, brains);
				}
			}
		});
	}

}
