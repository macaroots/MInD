/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.gui;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ListAgents extends AbstractAction {

    public void act(Object oArgs, Object callback) {
        Object[] args = (Object[]) oArgs;
        JFrame janela = (JFrame) args[0];
        JComboBox cbAgents = (JComboBox) args[1];
        cbAgents.removeAllItems();
        Ceed.getInstance().see("getAgents", null, new AbstractAction() {
            public void act(Object oAgents, Object c) {
                List<Agent> agents = (List<Agent>) oAgents;
                for (Agent agent: agents) {
                    cbAgents.addItem(agent);
                }
                janela.pack();
            }
        });
    }

}
