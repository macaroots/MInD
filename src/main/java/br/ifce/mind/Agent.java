/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind;

import br.ifce.brain.Symbol;
import br.ifce.mind.actions.Action;

public interface Agent {

	//void see(Symbol action, Object callback);

	void see(Object action);
	void see(Object action, Action callback);
	void see(Object action, Object args);
	void see(Object action, Object args, Object callback);
	void see(Symbol perception, Object callback);
	Mind getMind();
	void setMind(Mind mind);

}
