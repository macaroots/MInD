/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import java.util.List;

import br.ifce.brain.Brain;
import br.ifce.brain.Link;
import br.ifce.brain.Symbol;
import br.ifce.mind.actions.AbstractAction;

public class WriteBrain extends AbstractAction {

	/**
	 * @param [Brain b, String bookname, Symbol concept]
	 */
	public void act(Object object, Object callback) {
		try {
			// TODO desde o naive.Write que precisa do setArgs!
			// where to write
			Object [] args = (Object []) object;
			Brain brain = (Brain) args[0];
			// name of the book
			String bookname = (String) args[1];
			// [name, value] to write

			Object [] writeData;
			try {
				writeData = (Object []) args[2];
			}
			catch (ClassCastException e) {
				Symbol concept = (Symbol) args[2];
				writeData = new Object [] {concept.getType(), concept.getInfo()};
System.out.println("old_naiveWrite: " + concept.getType());
			}
			// escrever é criar a associação book-key-value
			Symbol book;
			Object key;
			Symbol value;

			key = writeData[0];
			book = new Symbol(bookname);
			Symbol relation = new Symbol(key);
			try {
				value = (Symbol) writeData[1];
			}
			catch (Exception e) {
				value = new Symbol(writeData[1]);
			}
			
			// acredita
			List<Symbol> books = (List<Symbol>) brain.get(book);
			if (!books.isEmpty()) {
				book = books.get(0);
			}
			List<Symbol> relations = brain.get(relation);
			if (!relations.isEmpty()) {
				relation = relations.get(0);
			}
			List<Symbol> concepts = brain.get(value);
			if (!concepts.isEmpty()) {
				value = concepts.get(0);
			}
			
			// conclui
			brain.tie(new Link(book, relation, value));
		}
		catch (Exception e) {
			this.getAgent().see("error", new Object [] {e, "Error on write: " + object + "!"});
			System.out.println(e.getMessage());
//			this.getAgent().see(new Symbol("Write error", e));
			this.doCallback(callback, false);
			return;
		}
		
		this.doCallback(callback, true);
	}

}
