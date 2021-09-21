/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;

public class GetName extends AbstractAction {

	public void act(Object object, Object callback) {
		doCallback(callback, ((NaiveMind) this.getAgent().getMind()).getName());
	}

}
