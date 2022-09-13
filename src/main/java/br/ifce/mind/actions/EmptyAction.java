/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions;

public class EmptyAction extends AbstractAction<Object> {

	public void act(Object args, Object callback) {
		callback(callback, null);
	}

}
