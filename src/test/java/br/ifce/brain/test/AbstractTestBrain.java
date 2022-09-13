/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain.test;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ifce.brain.Brain;
import br.ifce.brain.ListBrain;
import br.ifce.brain.Link;
import br.ifce.brain.Symbol;

public class AbstractTestBrain {

	Brain brain;
	@BeforeEach
	public void setup() {
		/**
		 * Choose your Brain
		 */
		/*/
		brain = new MySQLBrain("localhost", "teste_brain", "root", "admin");
		((MySQLBrain) brain).debug = true;
		/**/
		//brain = new FileBrain("C:\\Users\\Renato\\Desktop\\brain_test\\");
		brain = new ListBrain();
		System.out.println("TESTBRAIN SETUP");
		System.out.println(brain);
	}

	@Test
	public void testTieReason() {
		System.out.println("WARNING Precisa implementar TestBrain usando jUnit");
		System.out.println("0");
		System.out.println(brain);
		testTie(brain);
		testReason(brain);
		//System.out.println("1");
		//System.out.println(brain);
	}
		
	public void testTie(Brain brain) {
		for (int i = 0; i < 10; i++) {
			Symbol a, r, b;
			a = randomSymbol("a");
			r = randomSymbol("r");
			b = randomSymbol("b");
			Link link = new Link(a, r, b);
			//System.out.println("Tying: " + link);
			brain.tie(link);
		}		
		//System.out.println("2");
		//System.out.println(brain);
	}
	public void testReason(Brain brain) {
		//System.out.println("Test reason");
		Link search = null;
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		search = new Link(new Symbol("a1", null), null, null);
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		search = new Link(null, new Symbol(null, "r0"), null);
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		search = new Link(null, null, new Symbol("b0", null));
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		search = new Link(new Symbol("a1", null), null, new Symbol("b0", null));
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		search = new Link(new Symbol("a1", null), new Symbol(null, "r0"), new Symbol("b0", null));
		//System.out.println("Reasoning: " + search);
		for (Link n: brain.reason(search)) {
			//System.out.println(n);
			
		}
		//System.out.println("3");
		//System.out.println(brain);
		
	}
	public static Symbol randomSymbol(String type) {
		Symbol s = new Symbol();
		Random r = new Random();
		int i = r.nextInt(100);
		int resto = i % 3;
		s.setType(type + (resto));
		switch (resto) {
		case 0:
			s.setInfo(i);
			break;
		case 1:
			s.setInfo(type + i);
			break;
		case 2:
			s.setInfo(type + (i % 2));
			break;
		}
		return s;
	}
	
}
