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
import br.ifce.brain.Link;
import br.ifce.brain.Symbol;
import br.ifce.mind.actions.AbstractAction;

public class ReadBrain extends AbstractAction {
public static int count = 0;
	/**
	 * @param [Brain b, String bookname, String concept]
	 * @return List<Symbol>
	 */
	public void act(Object object, Object callback) {
		List<Symbol> meanings = new ArrayList<Symbol>();

		// where from read
		Object [] args = (Object []) object;
		Brain brain = (Brain) args[0];
		// name of the book
		String bookname = (String) args[1];
		// name of the concept to read
		Object key = args[2];

		Symbol book;
		book = new Symbol(bookname);
		
		Symbol relation = new Symbol(key);
		try {
			List<Link> nodes = brain.reason(new Link(book, relation));
			for (Link node: nodes) {
				Symbol meaning = node.getB();
				meanings.add(meaning);
			}
		} catch (Exception e) {
			this.getAgent().see("error", new Object [] {e, "Error on read!"});
		}
		this.doCallback(callback, meanings);
	}

}
