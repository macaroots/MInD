/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import br.ifce.brain.Symbol;
import br.ifce.brain.ListBrain;
import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.Mind;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.Action;

public class TestCeed {
	
	static Agent agent;
	
	@Test
	public void testGetAgent() {
		agent = Ceed.getAgent("x");
        assertEquals(agent.getClass(), Ceed.class);
	}
	@Test
	public void testGetSameAgent() {
		Agent agent2 = Ceed.getAgent("x");
		assertSame(agent, agent2);
    }
	@Test
	public void testGetAgentMultiple() {
		Agent agent3 = Ceed.getAgent("y");
		Agent agent4 = Ceed.getAgent("y");
		Agent agent5 = Ceed.getAgent("y");
		assertSame(agent3, agent4);
		assertSame(agent3, agent5);
	}
	@Test
	public void testGetAgentMultipleCallback() {
		Ceed.getAgent("y", new AbstractAction() {
            public void act(Object agent3, Object c) {
                Action assertAction = new AssertEquals(agent3);
                Ceed.getAgent("y", assertAction);
                Ceed.getAgent("y", assertAction);
                Ceed.getAgent("y", assertAction);
                Ceed.getAgent("y", assertAction);
                Ceed.getAgent("y", assertAction);
                Ceed.getAgent("y", assertAction);
            }
		});
	}
	@Test
	public void testNewAgent() {
		Agent agent2 = Ceed.newAgent("x");
		Agent agent3 = Ceed.newAgent("x");
		assertNotEquals(agent, agent2);
		assertNotEquals(agent3, agent2);
	}
	@Test
	public void testSubscribeNotify() {
		Agent ceed = Ceed.getInstance();
		Agent a = Ceed.getAgent("a");
		Action onNewAgent = new AbstractAction() {
            public void act(Object oEvent, Object c) {
                callback(new AssertInstance(Ceed.class), oEvent);
            }
		};
		a.see("set", new Object[] {"onNewAgent", onNewAgent});
		ceed.see("subscribe", new Object[] {"newAgent", a});
		Agent b = Ceed.getAgent("b");		
	}
	@Test
	public void testReadLibraries() {
		Agent agent = Ceed.getAgent("reader");
		agent.see("write", new Object[] {"a", new Symbol("object", 1)});
		agent.see("addLibrary", new ListBrain());
		agent.see("write", new Object[] {"a", new Symbol("object", 2)});
		agent.see("write", new Object[] {"b", new Symbol("object", 2)});
		agent.see("addLibrary", new ListBrain());
		agent.see("write", new Object[] {"c", new Symbol("object", 3)});
		agent.see("a", new AssertEquals(2));
		agent.see("b", new AssertEquals(2));
		agent.see("c", new AssertEquals(3));
	}
}
