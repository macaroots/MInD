/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.util.ArrayList;
import java.util.List;

import br.ifce.brain.Brain;
import br.ifce.brain.Symbol;
import br.ifce.mind.Agent;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class Read extends AbstractAction {

	public void act(Object readTarget, Object callback) {
		List<Symbol> representations;
		representations = new ArrayList<Symbol>();
		
		Agent agent = this.getAgent();
		
		Action reader = ((NaiveMind) agent.getMind()).getRead();

		agent.see("getLibraries", null, new AbstractAction() {
			public void act(Object librariesTarget, Object callback2) {
				String concept = (String) readTarget;
				List<Brain> brains = (List<Brain>) librariesTarget;
				List<String> booknames;
				if (concept.indexOf(".") == -1) {	
					booknames = ((NaiveMind) agent.getMind()).getNames();
				}
				else {
					String [] type = concept.split("\\.(?=[^\\.]*$)");
					booknames = new ArrayList<String>();
					
					booknames.add(type[0]);
					concept = type[1];
				}

				int totalBooks = brains.size() * booknames.size();
				List<String> booksRead = new ArrayList<String>();
//System.out.println("READING BRAIN: " + brains);
//System.out.println("READING BOOKS: " + booknames);
				
				for (int i = 0; i < brains.size(); i++) {
					Brain brain = brains.get(i);
					for (int j = booknames.size() - 1; j >= 0; j--) {
						String bookname = booknames.get(j);
//System.out.println("READING BRAIN: " + brain.getClass());
//System.out.println("READING: " + bookname + "." + concept);
						Object [] argsRead = {brain, bookname, concept};
						agent.see(reader, argsRead, new AbstractAction() {
							public void act(Object object, Object callback3) {
//System.out.println("RESULT BRAIN: " + brain.getClass() + ": " + object);
								try {
									representations.addAll((List<Symbol>) object);
								}
								catch (NullPointerException e) {
								}
								booksRead.add(bookname);
								if (booksRead.size() == totalBooks) {
									doCallback(callback, representations);
								}
							}
						});
					}
				}
			}
		});

	}
	
}
