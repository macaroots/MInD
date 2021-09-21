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

public class Console_v2 extends AbstractAction {
	public int i = 0;
	@Override
	public void act(Object args, Object callback) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Who you gonna call?");
		String agentName = scanner.next(); 
		Agent agent = Ceed.getAgent(agentName);
		
		String action, target;
		while (true) {
			System.out.print(agentName + "> ");
			action = scanner.next();
			if ("exit".equals(action)) {
				System.out.println("Bye!");
				scanner.close();
				System.exit(0);
			}
			target = scanner.nextLine().trim();
			agent.see(action, target, new AbstractAction() {
				@Override
				public void act(Object t, Object callback) {
					System.out.println("[" + (i++) + "]: " + t);
				}
			});
		}
	}

}
