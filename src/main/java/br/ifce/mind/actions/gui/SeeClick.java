/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.gui;

import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.Agent;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class SeeClick extends AbstractAction {

    public void act(Object _args, Object callback) {
        Object[] oArgs = (Object[]) _args;
        JComboBox cbAgents = (JComboBox) oArgs[0];
        JTextField txAction = (JTextField) oArgs[1];
        JTextField txArgs = (JTextField) oArgs[2];
        
        String action = txAction.getText();
        String args = txArgs.getText();
        
        Agent agent = (Agent) cbAgents.getSelectedItem();
        if (agent == null) {
            agent = this.getAgent();
        }
        agent.see(action, args, new AbstractAction() {
            public void act(Object r, Object c) {
                System.out.println(r);
            }
        });
    }

}
