/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileBrain implements Brain {

	String path;
	
	public FileBrain(String path) {
		this.path = path;
	}
	
	public void set(Symbol s) {
		try {
			if (s.getId() == 0) {
				int totalFiles = new File(this.getPath()).list().length;
				s.setId(++totalFiles);
			}
			FileWriter fos = new FileWriter(this.getFilename(s));
			fos.write(s.getInfo().toString());
			fos.close();
		} catch (IOException e) {
			new RuntimeException("Error while setting!", e);
		}
	}

	public List<Symbol> forget(Symbol s) {
		List<Symbol> symbols = this.get(s);
		for (Symbol symbol: symbols) {
			File file = new File(this.getFilename(symbol));
			if (file.exists()) {
				file.delete();
			}
		}
		return symbols;
	}

	public List<Symbol> get(Symbol s) {
		List<Symbol> symbols = new ArrayList<Symbol>();
		File dir = new File(this.getPath());
	    File[] files = dir.listFiles(new FilenameFilter() {
	    	String coringa = "[^_]*";
	    	String filePattern;
	    	public FilenameFilter init(Symbol s) {
    			filePattern = "" + ((s.getId() != 0) ? s.getId() : coringa);
	    		filePattern += "." + ((s.getType() != null) ? s.getType() : coringa);
	    		return this;
	    	}
	        public boolean accept(File dir, String name) {
	            return name.matches(filePattern);
	        }
	    }.init(s));
	    for (File file: files) {
			String info = null;
			//info = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			if (s.getInfo() == null || s.getInfo().toString().equals(info)) {
				String[] filename = file.getName().split("\\.", 2);
				int id = Integer.parseInt(filename[0]);
				String type = filename[1];
				Symbol symbol = new Symbol();
				symbol.setId(id);
				symbol.setType(type);
				symbol.setInfo(info);
				symbols.add(symbol);
			}
			
	    }
	    return symbols;
	}

	public void tie(Link l) {
		try {
			if (l.getA().getId() == 0) {
				this.set(l.getA());
			}
			if (l.getB().getId() == 0) {
				this.set(l.getB());
			}
			if (l.getR().getId() == 0) {
				this.set(l.getR());
			}
			String filename = this.getFilename(l);
			File file = new File(this.getPath() + filename);
			file.createNewFile();
		} catch (IOException e) {
			new RuntimeException("Error while tying!", e);
		}
	}
	
	public List<Link> untie(Link l) {
		List<Link> links = this.reason(l);
		File file = new File(this.getFilename(l));
		if (file.exists()) {
			file.delete();
		}
		return links;
	}

	
	public List<Link> reason(Link l) {
		List<Link> links = new ArrayList<Link>();
		File dir = new File(this.getPath());
	    File[] files = dir.listFiles(new FilenameFilter() {
	    	String filePattern = "";
	    	/**
	    	 * Filtrando a seguinte estrutura:
	    	 * 	a.type / a.info / r.type / r.info '.' b.type : b.info
	    	 */
	    	public FilenameFilter init(Link no) {
	    		String wildcard = "[^_]*";
	    		if (no == null || no.getA() == null) {
	    			filePattern += wildcard + "/" + wildcard + "/";
	    		}
	    		else {
	    			filePattern += no.getA().getType() + "/" + no.getA().getInfo() + "/";
	    		}
	    		if (no == null || no.getR() == null) {
	    			filePattern += wildcard + "/" + wildcard + "/";
	    		}
	    		else {
	    			filePattern += no.getR().getType() + "/" + no.getR().getInfo() + "/";
	    		}
	    		if (no == null || no.getB() == null) {
	    			filePattern += "\\." + wildcard + "/";
	    		}
	    		else {
	    			filePattern += "\\." + no.getB().getType();
	    		}
	    		return this;
	    	}
	        public boolean accept(File dir, String name) {
	            return name.matches(filePattern);
	        }
	    }.init(l));
	    System.out.println(Arrays.toString(files));
	    for (File file: files) {
	    	String [] filename = file.getName().split("\\.");;
	    	Link link = new Link();

			List<Symbol> as = this.get(new Symbol(Integer.parseInt(filename[0])));
			List<Symbol> bs = this.get(new Symbol(Integer.parseInt(filename[1])));
			List<Symbol> rs = this.get(new Symbol(Integer.parseInt(filename[2])));
			if (!as.isEmpty()) {
				link.setA(as.get(0));
			}
			if (!bs.isEmpty()) {
				link.setB(bs.get(0));
			}
			if (!rs.isEmpty()) {
				link.setR(rs.get(0));
			}
	    	links.add(link);
	    }
		return links;
	}


	private String getFilename(Symbol s) {
		return this.getPath() + s.getId() + "." + s.getType();
	}
	private String getFilename(Link l) {
		return l.getA().getId() + "." + l.getB().getId() + "." + l.getR().getId();
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
