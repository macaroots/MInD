/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.naive;

import br.ifce.mind.actions.AbstractAction;

public class Error extends AbstractAction {

	public void act(Object object, Object callback) {
		Object [] args = (Object []) object;
		Exception e = (Exception) args[0];
		try {
			String message = (String) args[1];
			System.err.println(message + " - " + e + ": " + e.getMessage());
		}finally{}
		int c = 0;
		for (StackTraceElement s: e.getStackTrace()) {
			System.out.println(s);
			if (c++ > 10) {
				break;
			}
		}
		//doCallback(callback, false);
	}

}
