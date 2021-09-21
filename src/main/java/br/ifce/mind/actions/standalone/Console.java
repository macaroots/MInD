/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import java.util.Scanner;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class Console extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Who you gonna call?");
		String agentName = scanner.next(); 
		Agent agent = Ceed.getAgent(agentName);

		Action readConsole = new AbstractAction() {
			public int i = 0;
			@Override
			public void act(Object t, Object callback) {
				System.out.println("[" + (i++) + "]: " + t);
				String action;
				String target;
				System.out.print(agentName + "> ");
				action = scanner.next();
				target = scanner.nextLine().trim();
				if ("exit".equals(action)) {
					System.out.println("Bye!");
					scanner.close();
					System.exit(0);
				}
				agent.see(action, target, this);
			}
		}; 
		agent.see(readConsole, null);
	}

}
