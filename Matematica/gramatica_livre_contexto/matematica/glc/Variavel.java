package matematica.glc;

import java.util.LinkedList;
import java.util.List;

public class Variavel {
	
	private char simbolo;

	public Variavel(char simbolo) {
		this.simbolo = simbolo;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.simbolo == ((Variavel) obj).simbolo;
	}

	@Override
	public String toString() {
		return "" + simbolo;
	}
	
	public static List<Variavel> variaveis(char ... simbolos) {
		List<Variavel> variaveis = new LinkedList<>();
		
		for (char simbolo : simbolos)
			variaveis.add(new Variavel(simbolo));

		return variaveis;
	}
}
