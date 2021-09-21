/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.io.Console;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class Require extends AbstractAction {

	public void act(Object _args, Object callback) {
		Object keyTmp = null;
		Object defaultValueTmp = null;
		try {
            Object [] args = (Object []) _args;
            keyTmp = args[0];
			defaultValueTmp = args[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			defaultValueTmp = "";
		} catch (ClassCastException e) {
            keyTmp = _args;
            defaultValueTmp = "";
		}
		Object defaultValue = defaultValueTmp;
		Object key = keyTmp;
		Agent agent = this.getAgent();
        Console console = System.console();
		agent.see("get", key, new AbstractAction() {
			public void act(Object required, Object callback2) {
				if (required != null) {
					doCallback(callback, required);
				}
				else {
					agent.see("get", "parent", new AbstractAction() {
						public void act(Object object, Object callback2) {
							Agent parent = (Agent) object;
							if (parent != null) {
								parent.see("get", key, new AbstractAction() {
									public void act(Object required, Object callback2) {
										if (required == null) {
											System.out.println(agent + " requires \"" + key + "\" [" + defaultValue + "]: ");
											required = console.readLine();
											if (required.equals("")) {
												required = defaultValue;
											}
                                            parent.see("set", new Object [] {key, required});
										}
                                        agent.see("set", new Object [] {key, required});
										doCallback(callback, required);
									}
								});
							}
							else {
								System.out.println(agent + " requires \"" + key + "\" [" + defaultValue + "]: ");
								String required = console.readLine();
								if (required.equals("")) {
									required = (String) defaultValue;
								}
								agent.see("set", new Object [] {key, required});
								doCallback(callback, required);
							}
						}
					});
				}
			}
		});
	}
	
}
