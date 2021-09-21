/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.mind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ifce.brain.Brain;
import br.ifce.brain.ListBrain;
import br.ifce.mind.actions.Action;
import br.ifce.mind.actions.naive.Act;
import br.ifce.mind.actions.naive.Error;
import br.ifce.mind.actions.naive.Get;
import br.ifce.mind.actions.naive.GetAll;
import br.ifce.mind.actions.naive.GetName;
import br.ifce.mind.actions.naive.GetNames;
import br.ifce.mind.actions.naive.ReadBrain;
import br.ifce.mind.actions.naive.See;
import br.ifce.mind.actions.naive.Set;
import br.ifce.mind.actions.naive.WriteBrain;

public class NaiveMind implements IntelligentMind {
	
	private List<Brain> brains;
	private List<String> names;
	private Action see, set, get, act, write, read;
	//private Ceed body;
	
	public NaiveMind() {
		this("", new ListBrain());
	}
	
	public NaiveMind(String name) {
		this(name, new ListBrain());
	}
	public NaiveMind(String [] names) {
		this(names, new ListBrain());
	}
	
	public NaiveMind(Brain brain) {
		this("", brain);
	}
	public NaiveMind(String name, Brain brain) {
		this((name == null || name.equals("")) ? null : name.split(","), brain);
	}

	public NaiveMind(String [] names, Brain brain) {
		this.brains = new ArrayList<Brain>();
		this.names = new ArrayList<String>();
		
		this.setBrain(brain);
		
		// TODO 
		Ceed body = new Ceed(this); 
		//this.setBody(body);

		// TODO comportamento da mente não deveria depender do corpo
		// de fato é só uma facilidade na programação.
		this.setSee(new See().init(body));
		this.setSet(new Set().init(body));
		this.setGet(new GetAll().init(body));
		this.setAct(new Act().init(body));
		this.setWrite(new WriteBrain().init(body));
		this.setRead(new ReadBrain().init(body));

		String [] fullName = this.getClass().getName().split("\\.");
		String className = fullName[fullName.length - 1];
		/**/
		className = this.getClass().getSimpleName();
		/**/
		if (names == null || names.length == 0) {
			names = new String [] {className};
		}
		if (!names[names.length - 1].equals(className)) {
			names = Arrays.copyOf(names, names.length + 1);
			names[names.length - 1] = className;
		}
		this.setNames(names);
		
		// aqui não tem problema usar o corpo, porque tá simplificando o uso
		// e escondendo o Symbol.
		/* Initial training */
		this.set("set", set, null);
		body.see("set", new Object [] {className + ".get", new Get()});
		body.see("set", new Object [] {className + ".getAll", get});

		body.see("set", new Object [] {className + ".getName", new GetName()});
		body.see("set", new Object [] {className + ".getNames", new GetNames()});
		body.see("set", new Object [] {className + ".error", new Error()});
	}

	public void see(Object perception) {
		see.act(perception, null);
	}
	public void see(Object perception, Object callback) {
		see.act(perception, callback);
	}

	public void set(Object key, Object value) {
		set(key, value, null);
	}
	public void set(Object key, Object value, Object callback) {
		Object [] concept = new Object [] {key, value};
		String name = this.getName();
		System.out.println("WARNING Arbitrary set! " + name + Arrays.toString(concept));
		System.out.println("\t\"Don't use agent as objects!\", use " + name + ".see(\"set\", new Object[] {key, value});");
		set.act(concept, callback);
	}
	
	public void get(Object key, Object callback) {
		get.act(key, callback);
	}

	public void act(Action action, Object args) {
		Object [] actArgs = {action, args};
		act.act(actArgs, null);
	}
	
	public void act(Action action, Object args, Object callback) {
		Object [] actArgs = {action, args};
		act.act(actArgs, callback);
	}

	public Brain getBrain() {
		return brains.get(0);
	}
	public void setBrain(Brain brain) {
		this.brains.add(0, brain);
	}
	public void addBrain(Brain brain) {
		this.brains.add(brain);
	}
	public Action getSee() {
		return see;
	}
	public void setSee(Action see) {
		this.see = see;
	}
	public Action getSet() {
		return set;
	}
	public void setSet(Action set) {
		this.set = set;
	}
	public Action getGet() {
		return get;
	}
	public void setGet(Action get) {
		this.get = get;
	}
	public Action getAct() {
		return act;
	}
	public void setAct(Action act) {
		this.act = act;
	}
	public Action getWrite() {
		return write;
	}

	public void setWrite(Action write) {
		this.write = write;
	}

	public Action getRead() {
		return read;
	}

	public void setRead(Action read) {
		this.read = read;
	}

	public void setName(String name) {
		this.names.add(0, name);
		this.set(name, this, null);
	}
	public String getName() {
		return this.getNames().get(0);
	}

	public List<Brain> getBrains() {
		return brains;
	}
	public void setBrains(List<Brain> brains) {
		this.brains = brains;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public void addName(String name) {
		this.names.add(name);
	}
	public void addName(String name, int i) {
		this.names.add(i, name);
	}
	public void setNames(String [] names) {
		this.setNames(new ArrayList<String>(Arrays.asList(names)));
	}

	public String toString() {
		String names = String.join(" ", this.getNames());
		return "(" + names.trim() + ")@" + this.hashCode();
	}
	
}
