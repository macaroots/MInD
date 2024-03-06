/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.ifce.mind.actions.AbstractAction;

class AssertInstance extends AssertEquals {
    public AssertInstance(Object expected) {
        super(expected, 1000);
    }
    public AssertInstance(Object expected, int milliseconds) {
        super(expected, milliseconds);
    }
    public void act(Object actual, Object callback) {
        this.defuse();
        assertEquals(expected, actual.getClass());
    }
}
