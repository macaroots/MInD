/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import java.util.List;

public interface Brain {

	public void set(Symbol s);
	public List<Symbol> forget(Symbol s);
	public List<Symbol> get(Symbol s);
	public void tie(Link n);
	public List<Link> untie(Link n);
	public List<Link> reason(Link n);
	
}
