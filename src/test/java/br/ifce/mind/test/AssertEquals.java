/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.ifce.mind.actions.AbstractAction;

class AssertEquals extends AbstractAction {
    Object expected;
    private Bomba bomba;
    public AssertEquals(Object expected) {
        this(expected, 1000);
    }
    public AssertEquals(Object expected, int milliseconds) {
        this.expected = expected;
        bomba = new Bomba(milliseconds);
    }
    public void act(Object actual, Object callback) {
        this.defuse();
        assertEquals(expected, actual);
    }
    
    public void defuse() {
        bomba.ativado = false;
    }
    
    class Bomba {
        boolean ativado = true;
        public Bomba(int milisegundos) {
            new java.util.Timer().schedule( 
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (ativado) {
                                fail("Error: Time limit");
                            }
                        }
                    }, 
                    milisegundos 
            );
        }
    }
}
