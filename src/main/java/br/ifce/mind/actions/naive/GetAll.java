/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import java.util.ArrayList;
import java.util.List;

import br.ifce.brain.Brain;
import br.ifce.brain.Symbol;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class GetAll extends AbstractAction {

	public void act(Object object, Object callback) {
		// TODO dá pra transformar em símbolo para permitir uma busca mais interessante?
		String concept = (String) object;

		NaiveMind agent = (NaiveMind) this.getAgent().getMind();
				
		Brain brain = agent.getBrain();
		//List<Brain> brains = agent.getBrains();
		List<String> booknames;
		if (concept.indexOf(".") == -1) {	
			booknames = agent.getNames();
		}
		else {
			String [] type = concept.split("\\.(?=[^\\.]*$)");		
			booknames = new ArrayList<String>();
			if ("super".equals(type[0])) {
				for (int i = 1; i < agent.getNames().size(); i++) {
					booknames.add(agent.getNames().get(i));
				}
			}
			else {
				booknames.add(type[0]);
			}
			concept = type[1];
		}
		
		Action reader = agent.getRead();
		List<Object> meanings = new ArrayList<Object>();
		List<String> booksRead = new ArrayList<String>();
		//for (Brain brain: brains) {
			for (int i = booknames.size() - 1; i >= 0; i--) {
				String bookname = booknames.get(i);
				Object [] args = {brain, bookname, concept};
				agent.act(reader, args, new AbstractAction() {
					public void act(Object object, Object callback2) {
						List<Symbol> symbols = (List<Symbol>) object;
						if (symbols != null && !symbols.isEmpty()) {
							for (Symbol s: symbols) {
								meanings.add(s.getInfo());
							}
							//break;
						}
						
						booksRead.add(bookname);
						if (booksRead.size() == booknames.size()) {
							this.doCallback(callback, meanings);
						}
					}
				});
			}
		//}
	}

}
