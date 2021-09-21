/** 
 * MInD - Model for Intelligence Development
 * 
 * Copyright Renato Lenz Costalima
 * Released under the AGPL-3.0 License
 * https://github.com/macaroots/MInD/blob/main/LICENSE
 */
package br.ifce.brain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListBrain implements Brain {

	private List<Symbol> symbols;
	private List<Link> links;

	public ListBrain() {
		symbols = new CopyOnWriteArrayList<Symbol>();
		symbols.add(null);
		links = new CopyOnWriteArrayList<Link>();
	}
	
	public void set(Symbol s) {
		if (!symbols.contains(s)) {
			s.setId(symbols.size());
			symbols.add(s);
		}
	}

	public List<Symbol> forget(Symbol s) {
		List<Symbol> forgotten = get(s);
		for (Symbol e: forgotten) {
			symbols.remove(e.getId());
		}
		return forgotten;
	}

	public List<Symbol> get(Symbol s) {
		List<Symbol> gotten = new ArrayList<Symbol>();
		for (Symbol i: symbols) {
			if (symbolsMatch(i, s)) {
				gotten.add(i);
			}
		}
		return gotten;
	}

	public void tie(Link l) {
		if (this.get(l.getA()).isEmpty()) {
			this.set(l.getA());
		}
		if (this.get(l.getR()).isEmpty()) {
			this.set(l.getR());
		}
		if (this.get(l.getB()).isEmpty()) {
			this.set(l.getB());
		}
		links.add(l);
	}

	public List<Link> untie(Link l) {
		List<Link> forgotten = reason(l);
		for (Link e: forgotten) {
			links.remove(e);
		}
		return forgotten;
	}

	public List<Link> reason(Link l) {
		List<Link> reasons = new ArrayList<Link>();
		for (Link i: links) {
			if (
				l == null ||
				(l.getA() == null || symbolsMatch(i.getA(), l.getA())) &&
				(l.getR() == null || symbolsMatch(i.getR(), l.getR())) &&
				(l.getB() == null || symbolsMatch(i.getB(), l.getB()))
			) {
				reasons.add(i);
			}
		}
		return reasons;
	}
	/**
	 * Verifica se dois símbolos são iguais
	 * Não fica em Simbolo.equals() porque essa é a interpretação deste cérebro
	 * Em outro contexto, símbolos podem ser considerados iguais por outro motivo
	 * @param a O símbolo a ser comparado da lista de símbolos
	 * @param b A pesquisa
	 * @return
	 */
	private boolean symbolsMatch(Symbol a, Symbol b) {
		boolean iguais = false;
		if (a != null && b != null) {
			if (
				(b.getId() != 0 && b.getId() == a.getId())
				&& (b.getType() == null || b.getType().equals(a.getType()))
				&& (b.getInfo() == null || b.getInfo().equals(a.getInfo()))
			) {
				iguais = true;
			}
			else {
				if (b.getInfo() == null) {
					if (b.getType() == null || b.getType().equals(a.getType())) {
						iguais = true;
					}
				}
				else {
					if (b.getType() == null) {
						if (b.getInfo().equals(a.getInfo())) {
							iguais = true;
						}
					}
					else {
						if (b.getType().equals(a.getType()) && b.getInfo().equals(a.getInfo())) {
							iguais = true;
						}
					}
				}
			}
		}
		
		return iguais;
	}
	public String toString() {
		String r = "Symbols:\n";
		for (Symbol s : symbols) {
			if (s != null) {
				r += s + "\n";
			}
		}
		r += "Links:\n";
		for (Link l: links) {
			if (l != null) {
				r += l + "\n";
			}
		}
		return r;
	}

}
