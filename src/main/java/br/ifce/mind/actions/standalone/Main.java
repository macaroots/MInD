/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.standalone;

import br.ifce.mind.Agent;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.brain.ListBrain;
import br.ifce.brain.Symbol;

import java.util.Arrays;

public class Main extends AbstractAction {

	@Override
	public void act(Object _args, Object callback) {
		String[] args = (String[]) _args;
		Agent ceed = this.getAgent();
				
        if (args.length == 0 || "--gui".equals(args[0])) {
            ceed.see("gui", args);
        }
		else if ("-mysql".equals(args[0])) {
            ceed.see("configMysql");
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
            this.act(newArgs, callback);
        }        
        else if ("--console".equals(args[0])) {
			ceed.see("console", args);
		}
		else if ("-l".equals(args[0])) {
	
			ceed.see("listen", args);
		}
		else {
			ceed.see("callAgent", args, new AbstractAction() {
				public void act(Object target, Object callback) {
					System.out.println(this.getAgent() + " says: " + target);	
				}
			});
		}
	}

}
