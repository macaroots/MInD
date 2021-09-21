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
import br.ifce.mind.Ceed;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

public class GUI extends AbstractAction {

    public void act(Object args, Object callback) {
        Agent agent = this.getAgent();
        
        JFrame janela = new JFrame();
        JComboBox cbAgents = new JComboBox();
        JTextField txAction = new JTextField(20);
        JTextField txArgs = new JTextField(40);
        JButton btSee = new JButton("see");
        JButton btAsk = new JButton("ask");
        
        janela.setTitle(agent.toString());
        janela.setLayout(new FlowLayout());
        janela.add(cbAgents);
        janela.add(txAction);
        janela.add(txArgs);
        janela.add(btSee);
        janela.add(btAsk);
        
        btSee.setMnemonic(KeyEvent.VK_ENTER);        
        btAsk.setMnemonic(KeyEvent.VK_A);
        btSee.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                agent.see("seeClick", new Object[] {cbAgents, txAction, txArgs});
            }
        });
        btAsk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                agent.see("askClick", new Object[] {cbAgents, txAction, txArgs});
            }
        });        
        
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.pack();
        janela.setVisible(true);
        
        Ceed.getInstance().see("subscribe", new Object[] {"newAgent", agent});
        agent.see("set", new Object[] {"onNewAgent", new AbstractAction() {
            public void act(Object a, Object callback) {
                agent.see("listAgents", new Object[] {janela, cbAgents}, callback);
            }
        }});
        agent.see("listAgents", new Object[] {janela, cbAgents}, callback);
    }

}
