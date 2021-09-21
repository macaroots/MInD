/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
/**
 * Representa o corpo que envolve o agente. 
 * 	As ações do NaiveAgent não estão usando o Ceed, porque trabalham em um nível abaixo.
 *  A vantagem é que é:
 *  	mais fácil de usar, esconde o Symbol
 *  	e permite acesso ao agente Ceed, atrvés do getInstance().  
 * */
package br.ifce.mind;

import br.ifce.brain.Symbol;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;
import br.ifce.mind.actions.standalone.InitInstance;

public class Ceed implements Agent {
	private static Agent ceed;
	private static Action initAction; 
	private Mind mind;

	public Ceed() {
	}
	public Ceed(String agentName) {
		this(new NaiveMind(agentName));
	}
	public Ceed(Mind agent) {
		this.setMind(agent);
	}
	/*/
	@Override
	public void see(Symbol action, Object callback) {
		//this.see(action, callback); errado
		this.see(action, null, callback);
	}
	/**/
	@Override
	public void see(Object action) {
		this.see(action, null, null);
	}
	@Override
	public void see(Object action, Action callback) {
		this.see(action, null, callback);
	}
	@Override
	public void see(Object action, Object args) {
		this.see(action, args, null);
	}
	@Override
	public void see(Object action, Object args, Object callback) {
		Symbol perception = new Symbol(action, args);
		this.getMind().see(perception, callback);
	}
	@Override
	public void see(Symbol perception, Object callback) {
		this.getMind().see(perception, callback);
	}
	@Override
	public Mind getMind() {
		return mind;
	}
	@Override
	public void setMind(Mind mind) {
		this.mind = mind;
	}
	
	public String toString() {
		return this.getMind().toString();
	}

	public static void newAgent(String name, Object callback) {
		Ceed.getInstance().see("newAgent", name, callback);
	}
	public static Agent newAgent(String name) {
		Agent[] agent = new Agent[1];
		Ceed.newAgent(name, new AbstractAction() {
			@Override
			public void act(Object args, Object callback) {
				agent[0] = (Agent) args;
			}
		});
		return agent[0];
	}
	public static void getAgent(String name, Object callback) {
		Ceed.getInstance().see("getAgent", name, callback);
	}
	public static Agent getAgent(String name) {
		Agent[] agent = new Agent[1];
		Ceed.getAgent(name, new AbstractAction() {
			@Override
			public void act(Object args, Object callback) {
				agent[0] = (Agent) args;
			}
		});
		return agent[0];
	}

	public static void getInstance(Action callback) {
		if (ceed == null) {
			ceed = new Ceed("Ceed");
			ceed.see("set", new Object[] {"initInstance", getInitAction()});
			ceed.see("initInstance", new AbstractAction() {
                @Override
                public void act(Object a, Object c) {
                    callback(callback, ceed);
                }
            });
		}
		else {
            callback.act(ceed, null);
        }
	}
	public static Agent getInstance() {
		Agent[] agent = new Agent[1];
		Ceed.getInstance(new AbstractAction() {
			@Override
			public void act(Object args, Object callback) {
				agent[0] = (Agent) args;
			}
		});
		return agent[0];
	}
	
	public static Action getInitAction() {
        if (initAction == null) {
            initAction = new InitInstance();
        }
        return initAction;
	}
	public static void setInitAction(Action action) {
        initAction = action;
	}

	public static void main(String[] args) {
		Agent ceed = Ceed.getInstance();
		ceed.see("main", args);
	}
}
