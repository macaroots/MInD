/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import org.junit.jupiter.api.BeforeEach;

import br.ifce.mind.Agent;
import br.ifce.mind.Ceed;
import br.ifce.mind.IntelligentMind;
import br.ifce.mind.test.DummyMind;

public class TestDummy extends TestAgent {
	@BeforeEach
	public void setUp() {
		agent = new Ceed(new DummyMind());
	}
}
