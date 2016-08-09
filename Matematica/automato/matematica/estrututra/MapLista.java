package matematica.estrututra;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Um mapa com uma chave <K> elementos são uma lista de tipo <E>
 */
public class MapLista<K, E> implements Iterable<List<E>> {

	private HashMap<K, List<E>> map;

	public MapLista() {
		this.map = new HashMap<K, List<E>>();
	}

	public void add(K chave, E valor) {
		getLista(chave).add(valor);
	}

	private List<E> getLista(K chave) {
		List<E> lista = map.get(chave);
		if (lista == null) {
			lista = new LinkedList<>();
			map.put(chave, lista);
		}

		return lista;
	}
	
	public List<E> get(K chave) {
		return getLista(chave);
	}
	
	public List<E> allElementos() {
		List<E> allElementos = new LinkedList<>();
		
		for (List<E> elementos : map.values())
			allElementos.addAll(elementos);
		
		return allElementos;
	}
	
	public HashMap<K, List<E>> getMap() {
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		MapLista<E, E> lista = (MapLista<E, E>) obj;
		
		return lista.map.equals(lista.map);
	}

	@Override
	public Iterator<List<E>> iterator() {
		return map.values().iterator();
	}
}
