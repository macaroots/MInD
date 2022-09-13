/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain.test;

import org.junit.jupiter.api.BeforeEach;

import br.ifce.brain.Brain;
import br.ifce.brain.ListBrain;

public class TestListBrain extends AbstractTestBrain {

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
	}
}
