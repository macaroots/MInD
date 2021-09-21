/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.ifce.brain.Symbol;
import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;

public class Ask extends AbstractAction {

	/**
	 * [Agent wantsToKnow, String concept]
	 */
	@Override
	public void act(Object object, Object callback) {
		Agent agent = this.getAgent();

		Agent wantsToKnowTmp; // deve saber hear([word, answer])
		String conceptTmp;
		Symbol perception;
		try {
			Symbol args = (Symbol) object;
			wantsToKnowTmp = agent;
			conceptTmp = (String) args.getType();
		} catch (ClassCastException e0) {
			try { 
				wantsToKnowTmp = agent;
				conceptTmp = (String) object;
			}
			catch (ClassCastException e) {
				Object [] args = (Object []) object;
				try {
					wantsToKnowTmp = (Agent) args[1];
					perception = (Symbol) args[0];
					conceptTmp = (String) perception.getType();
				} catch (Exception e2) {
					wantsToKnowTmp = agent;
					conceptTmp = (String) args[0];			
				}
			}
		}
		String concept = conceptTmp;
		Agent wantsToKnow = wantsToKnowTmp;
		
		// abre janela, memo e botões ok e cancelar
		JFrame frame = new JFrame(wantsToKnow.toString() + " - What's \"" + concept + "\"?");
		JTextField txType = new JTextField("", 5);
		JTextArea txCodigo = new JTextArea(); //14, 43);
		JScrollPane spCodigo = new JScrollPane(txCodigo);
		JButton btOk = new JButton("Ok");
		JButton btCancel = new JButton("Cancel");
		btOk.setMnemonic(KeyEvent.VK_ENTER);
		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.add(txType);
		panel.add(btOk);
		panel.add(btCancel);
		
		GroupLayout layout = new GroupLayout(frame.getContentPane());
		frame.setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addComponent(spCodigo)
					.addComponent(panel)
					/*
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(txType)
						.addComponent(btOk)
						.addComponent(btCancel)
					)
		*/
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(spCodigo)
				.addComponent(panel)/*
				.addGroup(layout.createSequentialGroup()
					.addComponent(txType)
					.addComponent(btOk)
					.addComponent(btCancel)
				)*/
		);
				
		frame.setVisible(true);
		panel.setMaximumSize(panel.getSize());
		panel.setMinimumSize(panel.getSize());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		btOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Symbol answer = new Symbol(txType.getText(), txCodigo.getText());
				wantsToKnow.see("hear", new Object [] {concept, answer}, new AbstractAction() {
					public void act(Object object, Object callback2) {
						Boolean learned = (Boolean) object;						
						if (learned) {
							frame.dispose();
							doCallback(callback, learned);
						}
					}
				});
			}
		});
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				doCallback(callback, false);
			}
		});
		KeyAdapter esc = new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {  // handler
				if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
					doCallback(callback, false);
				}
			} 
		};
		frame.addKeyListener(esc);
		txType.addKeyListener(esc);
		txCodigo.addKeyListener(esc);
		btOk.addKeyListener(esc);
		btCancel.addKeyListener(esc);
		
		// carrega a classe anterior ou Action.txt
		wantsToKnow.see("read", concept, new AbstractAction() {
			public void act(Object object, Object callback2) {
				List<Symbol> representations = (List<Symbol>) object;
				if (representations != null && !representations.isEmpty()) {
					Symbol representation = representations.get(representations.size() - 1);
					txType.setText(representation.getType().toString());
					txCodigo.setText(representation.getInfo().toString());
					frame.pack();
				}
				else {
					wantsToKnow.see("read", "EmptyAction", new AbstractAction() {
						public void act(Object object, Object callback2) {
							String type = "java";
							String info = "";
							List<Symbol> representations = (List<Symbol>) object;	
							if (representations != null && !representations.isEmpty()) {
								Symbol representation = representations.get(representations.size() - 1);
								type = representation.getType().toString();
								info = representation.getInfo().toString();
								String classname = concept.substring(0, 1).toUpperCase() + concept.substring(1);
								info = info.replaceAll("EmptyAction", classname);
							}
							else {
								txCodigo.setColumns(43);
							}
							txType.setText(type);
							txCodigo.setText(info);
							frame.pack();
						}
					});
				}
			}
		});
		/*
		txType.setMaximumSize();
		txType.setMinimumSize(txType.getSize());*/

		frame.pack();
	}
	
}
