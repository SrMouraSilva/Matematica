package matematica.automato.transicao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import matematica.automato.afn.Estado;
import matematica.automato.alfabeto.Alfabeto;
import matematica.estrututra.MapLista;

public class FuncaoTransicaoPrintBuilder {

	public static StringBuilder build(Alfabeto alfabeto, FuncaoTransicao funcao) {
		StringBuilder builder = new StringBuilder();
		
		Alfabeto alfabetoComε = gerarAlfabetoComε(alfabeto);
		
		Collection<Estado> estados = ordenarEstados(funcao.estados());
		
		for (Estado estado : estados) {
			builder.append(estado).append("\n");

			Collection<TransicaoDeEstado> transicoes = funcao.transicoesAPartirDe(estado);
			builder.append(buildTransicoes(transicoes, alfabetoComε));
		}
		
		return builder;
	}

	private static Alfabeto gerarAlfabetoComε(Alfabeto alfabeto) {
		List<Character> simbolos = alfabeto.getSimbolos();
		simbolos.add(Alfabeto.ε);
		
		return new Alfabeto(alfabeto.getSimbolos());
	}

	private static StringBuilder buildTransicoes(Collection<TransicaoDeEstado> transicoes, Alfabeto alfabeto) {
		StringBuilder builder = new StringBuilder();
		
		MapLista<Character, TransicaoDeEstado> transicoesPorSimbolo = organizarTransicoesPorSimbolo(transicoes);
		for (Character character : alfabeto) {
			Collection<Estado> destinos = getDestinosDe(transicoesPorSimbolo.get(character));
			String destinosFormatado = !destinos.isEmpty() ? destinos.toString() : "Ø";
			builder.append(" - ").append(character).append(": ").append(destinosFormatado).append("\n");
		}
		
		return builder;
	}
	
	private static Collection<Estado> ordenarEstados(Collection<Estado> estados) {
		Comparator<Estado> comparator = (Estado p1, Estado p2) -> new Integer(p1.index).compareTo(p2.index);
		List<Estado> sorted = new ArrayList<>(estados);
		sorted.sort(comparator);
        
        return sorted;
	}

	private static MapLista<Character,TransicaoDeEstado> organizarTransicoesPorSimbolo(Collection<TransicaoDeEstado> transicoes) {
		MapLista<Character, TransicaoDeEstado> transicoesOrganizadas = new MapLista<>();

		for (TransicaoDeEstado transicao : transicoes)
			transicoesOrganizadas.add(transicao.simbolo, transicao);
		
		return transicoesOrganizadas;
	}
	
	private static Collection<Estado> getDestinosDe(List<TransicaoDeEstado> transicoes) {
		Collection<Estado> estados = new LinkedList<>();

		for (TransicaoDeEstado transicaoDeEstado : transicoes)
			estados.add(transicaoDeEstado.destino);
		
		return estados;
	}	
}
