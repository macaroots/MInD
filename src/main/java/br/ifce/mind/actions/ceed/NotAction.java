/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import br.ifce.brain.Symbol;
import br.ifce.mind.actions.AbstractAction;

public class NotAction extends AbstractAction {

	public void act(Object _args, Object callback) {
        Object[] args = (Object[]) _args;
        Object perception = args[0];
        Object meaning = args[1];
		callback(callback, meaning);
	}

}
