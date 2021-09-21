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

public class AskClick extends AbstractAction {

	public void act(Object args, Object callback) {
        Object[] oArgs = (Object[]) args;
        JComboBox cbAgents = (JComboBox) oArgs[0];
        JTextField txAction = (JTextField) oArgs[1];
        JTextField txArgs = (JTextField) oArgs[2];
        
        String action = txAction.getText();
        
        Agent agent = (Agent) cbAgents.getSelectedItem();
        agent.see("ask", action, callback);
	}

}
