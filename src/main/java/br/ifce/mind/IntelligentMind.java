/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind;

public interface IntelligentMind extends Mind {

	/**
	 * Learns a concept
	 * @param concept
	 * @return 
	 */
	public abstract void set(Object key, Object value);
	public abstract void set(Object key, Object value, Object callback);

}
