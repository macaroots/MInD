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
import br.ifce.mind.actions.ceed.Live;
import br.ifce.brain.ListBrain;
import br.ifce.brain.Symbol;

import java.io.File;

public class DefaultConfig extends AbstractAction {

	@Override
	public void act(Object args, Object callback) {
		Agent agent = this.getAgent();
		
		agent.see("addLibrary", new ListBrain());
		Symbol empty = new Symbol("java", "package br.ifce.mind.actions;\n" + 
            "\nimport br.ifce.mind.actions.AbstractAction;\n" + 
            "\npublic class EmptyAction extends AbstractAction {\n" + 
            "	public void act(Object args, Object callback) {\n" + 
            "		callback(callback, null);\n" + 
            "	}\n" +
            "}"
        );
		agent.see("write", new Object[] {"NaiveMind.EmptyAction", empty});
		agent.see("write", new Object[] {"NaiveMind.live", new Symbol("object", new Live())});
		
		
		String absolutePath = new File(".").getAbsolutePath();
		String defaultRootPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 1) + "live" + File.separator;
		String defaultBinPath = defaultRootPath;
		String defaultClasspath = System.getProperty("java.class.path") + File.pathSeparatorChar + defaultBinPath;
		
		System.out.println("Configuring " + agent);
		System.out.println("srcPath: " + defaultRootPath);
		System.out.println("binPath: " + defaultBinPath);
		System.out.println("classpath: " + defaultClasspath);
		
		
		agent.see("set", new Object [] {"srcPath", defaultRootPath});
		agent.see("set", new Object [] {"binPath", defaultBinPath});
		agent.see("set", new Object [] {"classpath", defaultClasspath});
		
        doCallback(callback, null);
	}

}
