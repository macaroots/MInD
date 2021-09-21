/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import java.util.List;

import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;

public class Get extends AbstractAction {

	public void act(Object object, Object callback) {
		String concept = (String) object;
		NaiveMind agent = (NaiveMind) this.getAgent().getMind();
		agent.get(concept, new AbstractAction() {
			public void act(Object object, Object callback2) {
				Object meaning = null;
				List meanings = (List) object; 
				if (meanings != null && !meanings.isEmpty()) {
					meaning = meanings.get(meanings.size() - 1);
				}
				this.doCallback(callback, meaning);
			}
		});
	}

}
