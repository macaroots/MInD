/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import java.util.Arrays;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;

public class CallAgent extends AbstractAction {

	@Override
	public void act(Object _args, Object callback) {
		String[] args = (String[]) _args;
		Agent agent;
		String agentName = "ceed";
		String actionName = "gui";
		Object info = null;
		String arg = "";
		
		switch (args.length) {
		default:
			info = Arrays.copyOfRange(args, 2, args.length);
			if (((String []) info).length > 1) {
				arg = Arrays.toString((String []) info);
			}
			else {
				arg = ((String []) info)[0];
				info = arg;
			}
		case 2:
			actionName = args[1];
		case 1:
			agentName = args[0];
			break;
		case 0:
		}
		System.out.println("Calling '" + agentName + "." + actionName + "(" + arg + ")'");
		agent = Ceed.getAgent(agentName);
		agent.see(actionName, info, callback);
		System.out.println("Wait for it to callback or not...");

	}

}
