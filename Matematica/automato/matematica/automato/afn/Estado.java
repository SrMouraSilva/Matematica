package matematica.automato.afn;

import java.util.ArrayList;
import java.util.Collection;

public class Estado {
	/**
	 * Índice único de um estado em um AFN para facilitar a visualização
	 */
	public final int index;
	
	public Estado(int index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		return "q" + index;
	}
	
	public static Collection<Estado> estados(Estado ... estados) {
		Collection<Estado> collection = new ArrayList<>();
		
		for (Estado estado : estados)
			collection.add(estado);
		
		return collection;
	}
}
