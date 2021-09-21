/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

public class MySQLBrainException extends RuntimeException {

	public MySQLBrainException(String message, Exception cause) {
		super(message, cause);
	}

}
