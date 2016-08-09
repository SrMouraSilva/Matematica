package matematica.automato.transicao;

import matematica.automato.afn.Estado;
import matematica.automato.alfabeto.Alfabeto;

/**
 * Representa a passagem de um estado inicial para um posterior por um símbolo
 */
public class TransicaoDeEstado {
	public final Estado origem;
	public final Estado destino;
	public final char simbolo;

	public TransicaoDeEstado(Estado origem, Estado destino, char simbolo) {
		this.origem = origem;
		this.destino = destino;
		this.simbolo = simbolo;
	}
	
	/**
	 * @return simbolo passado é O MESMO que o símbolo desta transição?
	 *         Observe que não leva em conta transições ε
	 */
	public boolean aceita(char simbolo) {
		return this.simbolo == simbolo;
	}
	
	public boolean isε() {
		return this.simbolo == Alfabeto.ε;
	}
	
	@Override
	public String toString() {
		return origem + " -( "+ simbolo +" )-> " + destino;
	}
	
	@Override
	public boolean equals(Object obj) {
		TransicaoDeEstado o = (TransicaoDeEstado) obj;
		
		return this.origem == o.origem
			&& this.destino == o.destino
			&& this.simbolo == o.simbolo;
	}
}
