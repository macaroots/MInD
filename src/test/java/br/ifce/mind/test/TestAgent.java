/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.Mind;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;

public abstract class TestAgent {
	static Agent agent;
	@BeforeEach
	public abstract void setUp();
	
	@Test
	public void testMath() throws InterruptedException {
		// Sum should fail!
		agent.see("sum", new Object [] {1f, 2f}, new AssertEquals(null));
		
		// Teaching techniques
		agent.see("set", new Object [] {"sum", new AbstractAction() {
			public void act(Object object, Object callback) {
				float soma = 0.0f;
				for (Object n: (Object []) object) {
					soma += (Float) n;
				}
				this.callback(callback, soma);
			}
		}});
		agent.see("set", new Object [] {"mult", new AbstractAction() {
			public void act(Object object, Object callback) {
				float soma = 1.0f;
				for (Float n: (Float []) object) {
					soma *= n;
				}
				this.callback(callback, soma);
			}
		}});
		
		// Sum should succeed!
		agent.see("sum", new Object [] {1f, 2f}, new AssertEquals(3f));
	
		Random random = new Random();
		for (int i = 1; i < 11; i++) {
			int tam = random.nextInt(10);
			Float [] numbers = new Float [tam];
			float sum = 0.0f;
			float mult = 1.0f;
			for (int j = 0; j < tam; j++) {
				float n = random.nextFloat();
				sum += n;
				mult *= n;
				numbers[j] = n;
			}
			agent.see("sum", numbers, new AssertEquals(sum));
			agent.see("mult", numbers, new AssertEquals(mult));
		}
		

		Thread.sleep(2000);
	}

}
