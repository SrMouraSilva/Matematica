package matematica.automato.alfabeto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Alfabeto implements Iterable<Character> {

	/** Vazio */
	public static final char ε = 'ε';

	private List<Character> simbolos;

	public Alfabeto(char ... simbolos) {
		this.simbolos = new ArrayList<>();
		
		for (char simbolo : simbolos)
			this.simbolos.add(simbolo);
	}
	
	public Alfabeto(List<Character> simbolos) {
		this.simbolos = simbolos;
	}
		
	public List<Character> getSimbolos() {
		return simbolos;
	}
	
	@Override
	public String toString() {
		return simbolos.toString();
	}

	public boolean contains(char simbolo) {
		return simbolos.contains(simbolo);
	}

	@Override
	public Iterator<Character> iterator() {
		return this.simbolos.iterator();
	}
}
