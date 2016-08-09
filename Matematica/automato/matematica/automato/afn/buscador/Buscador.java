package matematica.automato.afn.buscador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import matematica.automato.afn.AFN;
import matematica.automato.afn.Estado;
import matematica.automato.afn.exception.CriacaoAFNException;
import matematica.automato.alfabeto.Alfabeto;
import matematica.automato.transicao.FuncaoTransicao;

public class Buscador {

	public static boolean buscar(String texto, String parte) {
		List<Estado> estados = criarEstados(parte);
		
		Alfabeto alfabeto = descobrirAlfabeto(texto);
		
		FuncaoTransicao δ = gerarFuncaoTransicao(estados, parte, alfabeto);
		
		Estado estadoInicial = getEstadoInicial(estados);
		Estado estadoFinal = getEstadoFinal(estados);
		
		Collection<Estado> estadosFinais = Estado.estados(estadoFinal);
		
		AFN afn;

		try {
			afn = new AFN(estados, alfabeto, δ, estadoInicial, estadosFinais);
		} catch (CriacaoAFNException e) {
			return false;
		}
		return afn.aceita(texto);
	}

	private static Alfabeto descobrirAlfabeto(String texto) {
		List<Character> simbolos = new ArrayList<>();
		
		for (char caractere : texto.toCharArray())
			if (!simbolos.contains(caractere))
				simbolos.add(caractere);

		return new Alfabeto(simbolos);
	}
	
	private static FuncaoTransicao gerarFuncaoTransicao(List<Estado> estados, String parte, Alfabeto alfabeto) {
		char[] partes = parte.toCharArray();
		
		FuncaoTransicao δ = new FuncaoTransicao();
		
		for (int i=0, j=1; i<estados.size()-1 && j<estados.size(); i++, j++) {
			Estado estadoOrigem = estados.get(i);
			Estado estadoAlcancavel = estados.get(j);
			
			δ.addTransicao(estadoOrigem, estadoAlcancavel, partes[i]);
		}
		
		Estado inicial = getEstadoInicial(estados);
		δ.addTransicaoΣ(inicial, inicial, alfabeto);
		
		if (!parte.isEmpty()) {
			Estado estadoFinal = getEstadoFinal(estados);
			δ.addTransicaoΣ(estadoFinal, estadoFinal, alfabeto);
		}
		
		return δ;
	}

	private static Estado getEstadoInicial(List<Estado> estados) {
		return estados.get(0);
	}

	private static Estado getEstadoFinal(List<Estado> estados) {
		return estados.get(estados.size()-1);
	}

	/**
	 * Cria estados, onde |estados| = |parte| + 1
	 */
	private static List<Estado> criarEstados(String parte) {
		List<Estado> estados = new ArrayList<>();
		
		for (int i = 0; i < parte.length()+1; i++)
			estados.add(new Estado(i));
		
		return estados;
	}

}
