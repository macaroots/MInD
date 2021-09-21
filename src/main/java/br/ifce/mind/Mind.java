/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind;

public interface Mind {

	void see(Object perception);
	void see(Object perception, Object callback);
	/**
	 * Gets the meaning of the concept for the agent
	 * @param concept
	 * @return a List of meanings
	 */
	public abstract void get(Object key, Object callback);

	
}
