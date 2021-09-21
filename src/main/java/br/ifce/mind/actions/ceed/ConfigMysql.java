/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.brain.Brain;
import br.ifce.brain.MySQLBrain;
import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class ConfigMysql extends AbstractAction {

	public void act(Object target, Object callback) {
		Agent agent = this.getAgent();

        agent.see("require", new Object [] {"host", "localhost"}, new AbstractAction() {
            public void act(Object object, Object callback2) {
                String host = (String) object;
                agent.see("require", new Object [] {"user", "root"}, new AbstractAction() {
                    public void act(Object object, Object callback2) {
                        String user = (String) object;
                        agent.see("requirePassword", new Object [] {"password"}, new AbstractAction() {
                            public void act(Object object, Object callback2) {
                                String password = (String) object;
                                agent.see("require", new Object [] {"db", "jmind"}, new AbstractAction() {
                                    public void act(Object object, Object callback2) {
                                        String db = (String) object; 
                                        agent.see("require", new Object [] {"port", "3306"}, new AbstractAction() {
                                            public void act(Object object, Object callback2) {
                                                String port = (String) object; 
                                                Brain brain = new MySQLBrain(host, db, user, password, port);
                                                agent.see("addLibrary", brain);
                                                doCallback(callback, brain);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
	}

}
