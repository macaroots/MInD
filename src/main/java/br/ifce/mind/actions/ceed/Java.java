/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind.actions.ceed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.ToolProvider;
import javax.tools.JavaCompiler;

import br.ifce.mind.Agent;
import br.ifce.mind.NaiveMind;
import br.ifce.mind.actions.AbstractAction;
import br.ifce.mind.actions.EmptyAction;

// TODO não compilar todo mundo só o último!
public class Java extends AbstractAction/* Interpret */{
	public static int tmp = 0;
	private URLClassLoader urlcl;
	@Override
	public void act(Object codeObject, Object callback) {
		Agent agent = this.getAgent();
		
		// GET DEFAULTS
		String absolutePath = new File(".").getAbsolutePath();
		String defaultRootPath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator) + 1) + "live" + File.separator;
		String defaultLibpath = defaultRootPath.replaceFirst("/bin", "");
		String defaultClasspath = System.getProperty("java.class.path");
		String defaultBinPath = defaultRootPath;
		
		agent.see("require", new Object [] {"srcPath", defaultRootPath}, new AbstractAction() {
			public void act(Object caminhoObject, Object callback2) {
				agent.see("require", new Object [] {"classpath", defaultClasspath}, new AbstractAction() {
					public void act(Object classpathObject, Object callback2) {
						agent.see("require", new Object [] {"binPath", defaultBinPath}, new AbstractAction() {
							public void act(Object caminhoActionObject, Object callback2) {
								String code = (String) codeObject; // Entrada: o código fonte da classe
								Object meaning = null; // Saída: a instância da classe descrita
								String caminho, classpath, packageName, caminhoAction;
								caminho = (String) caminhoObject;
								classpath = (String) classpathObject;
								caminhoAction = (String) caminhoActionObject;
								if (caminhoAction == null || caminhoAction.equals("")) {
									caminhoAction = caminho;
								}
								
								Class<?> actionClass = EmptyAction.class;
								packageName = actionClass.getPackage().getName();
								
								// tem que descobrir o nome da classe para poder criar o arquivo.
								String shortClassname;
								String classname;
								int pClass, pSpace;
								String classString = " class ";
								pClass = code.indexOf(classString) + classString.length();
								pSpace = code.indexOf(" ", pClass + 1);
								classname = code.substring(pClass, pSpace);
								// capitalize first letter
								
								int lastDot = classname.lastIndexOf('.');
								String subPackage = "";
								if (lastDot > -1) {
									subPackage = "." + classname.substring(0, lastDot);
									shortClassname = classname.substring(lastDot + 1);
									code = code.replace(classname, shortClassname);
									classname = shortClassname;
								}
								
								// definindo o nome do pacote
								int pPackage, pEnd;
								String codeWithoutPackage;
								String packageString = "package ";
								pPackage = code.indexOf(packageString);
								if (pPackage != -1) {
									pPackage += packageString.length();
									pEnd = code.indexOf(";", pPackage + 1);
									packageName = code.substring(pPackage, pEnd);
									codeWithoutPackage = code.substring(pEnd + 1);
								}
								else {
									codeWithoutPackage = code;
								}
								if ("br.ifce.mind.actions".equals(packageName)) {
									// TODO deveria chamar agent.see('getName')
									// TODO na verdade, deveria pedir pro FileBrain
									packageName += ".live." + ((NaiveMind) agent.getMind()).getName().toLowerCase();
									packageName += subPackage;
								}
								code = "package " + packageName + ";" + codeWithoutPackage;
								
								// define o caminho para o arquivo
								String pastaPacote = packageName.replaceAll("\\.", "/");
								pastaPacote += File.separator;
								// TODO tirar caracteres especiais do arquivo
								String nomeArquivo = classname + ".java";
								
								try {
									File directory = new File(caminho + pastaPacote);
									if (!directory.exists()) {
										directory.mkdirs();
									}
									
									directory = new File(caminhoAction + pastaPacote);
									if (!directory.exists()) {
										directory.mkdirs();
									}
									
									File file = new File(caminho + pastaPacote + nomeArquivo);
									PrintWriter pw = new PrintWriter(file);
									pw.print(code);
									pw.close();
									
									if (meaning == null) {
										// compila
										int compilationResult = compileSource(new String[] {
												"-cp", classpath,
												"-d", caminhoAction,
												caminho + pastaPacote + nomeArquivo
										});
										
										if (compilationResult == 0) {
											meaning = getInstance(packageName, classname, caminhoAction, classpath);
										}
									}
								} catch (FileNotFoundException e) {
									System.err.println("java - Não achou arquivo: " + e.getMessage());
									System.err.println("Caminho: " + caminho);
									System.err.println("Caminho action: " + caminhoAction);
									System.err.println("Classpath: " + classpath);
									
								}
								
								doCallback(callback, meaning);
							}
						});
					}
				});
			}
		});
	}
    private int compileSource(String [] args) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            return compiler.run(null, null, null, args);
        }
        catch (NullPointerException e) {
            return com.sun.tools.javac.Main.compile(args);
        }
    }
	
	public Object getInstance(String packageName, String classname, String rootPath, String classpath) {
		Object meaning = null;
		try {
			String [] cps = classpath.split(File.pathSeparatorChar + "");
			URL [] args = new URL[cps.length + 1];
			args[0] = new URL("file:///" + rootPath);
			for (int i = 0; i < cps.length; i++) {
				args[i + 1] = new URL("file:///" + cps[i]);
			}
			urlcl = URLClassLoader.newInstance(args, AbstractAction.class.getClassLoader());
			
			Class<?> conceptClass = Class.forName(packageName + "." + classname, true, urlcl);
			
			meaning = conceptClass.newInstance();
			
			
		} catch (ClassNotFoundException e) {
			this.getAgent().see("error", new Object [] {e, "Error on java!"});
		} catch (InstantiationException e) {
			this.getAgent().see("error", new Object [] {e, "Error on java!"});
		} catch (IllegalAccessException e) {
			this.getAgent().see("error", new Object [] {e, "Error on java!"});
		} catch (MalformedURLException e) {
			this.getAgent().see("error", new Object [] {e, "Error on java!"});
		} catch (Exception e) {
			this.getAgent().see("error", new Object [] {e, "Error on java!"});
		}
		return meaning;
	}
	
	protected void finalize() throws Throwable {
		urlcl.close();
		super.finalize();
	}

}
