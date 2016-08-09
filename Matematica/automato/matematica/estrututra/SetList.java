package matematica.estrututra;

import java.util.Collection;
import java.util.HashMap;

/**
 * Lista de elementos não repetíveis
 */
public class SetList<E> {
	
	private HashMap<E, E> map;

	public SetList() {
		this.map = new HashMap<>();
	}
	
	public void addAll(Collection<E> elementos) {
		for (E elemento : elementos)
			this.add(elemento);
	}
	
	public void add(E elemento) {
		this.map.put(elemento, elemento);
	}
	
	public boolean contains(E elemento) {
		return this.map.get(elemento) != null;
	}

	public Collection<E> list() {
		return map.values();
	}
}
