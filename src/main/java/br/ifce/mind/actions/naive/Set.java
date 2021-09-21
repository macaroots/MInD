/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import br.ifce.brain.Brain;
import br.ifce.brain.Symbol;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class Set extends AbstractAction {

	public void act(Object object, Object callback) {
		Object [] writeData;
		String type;
		try {
			writeData = (Object []) object;
		}
		catch (ClassCastException e) {
			try {
				String [] args = ((String) object).split("=");
				args[0] = args[0].trim();
				args[1] = args[1].trim();
				writeData = args;
				this.getAgent().see("warning", "setString: " + object);
			} catch (ClassCastException e2) {
				Symbol concept;
				try {
					concept = (Symbol) object;
					this.getAgent().see("warning", "old_set: " + this.getAgent() + " - " + concept.getType());
				} catch (ClassCastException e3) {
					concept = new Symbol(object);
				}
				writeData = new Object [] {concept.getType(), concept.getInfo()};
			}
		}
		type = (String) writeData[0];

		NaiveMind agent = (NaiveMind) this.getAgent().getMind();
		Brain brain = agent.getBrain();
		String bookname;

		if (type.indexOf(".") == -1) {	
			bookname = agent.getName();
		}
		else {
			String [] types = type.split("\\.(?=[^\\.]*$)");
			//this.getAgent().see("debug", "livrosSet: " + Arrays.toString(types));
			bookname = types[0];
			writeData[0] = types[1];
		}
/*/
		Map<Object, List<Object>> knowledge = agent.getKnowledge();
		List<Object> knowledgeKey = knowledge.get(writeData[0]);
		if (knowledgeKey == null) {
			knowledgeKey = new ArrayList<Object>();
			knowledge.put(writeData[0], knowledgeKey);
		}
		knowledgeKey.add(writeData[1]);
/**/		
		Action writer = agent.getWrite();
		Object [] args = {brain, bookname, writeData};
		agent.act(writer, args, callback);
	}

}
