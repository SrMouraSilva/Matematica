package matematica.automato.transicao;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import matematica.automato.afn.Estado;
import matematica.automato.alfabeto.Alfabeto;
import matematica.estrututra.MapLista;
import matematica.estrututra.SetList;

public class FuncaoTransicao implements Iterable<TransicaoDeEstado> {
	/** Transições agrupadas pelo estado de origem */
	private MapLista<Estado, TransicaoDeEstado> transicoes;

	public FuncaoTransicao() {
		this.transicoes = new MapLista<>();
	}

	/* =========================================
	 * Transições
	 * =========================================*/
	/**
	 * Adicionar transições à função de transição.
	 * 
	 * É no plural por gerar uma transição para cada símbolo passado
	 */
	public void addTransicoes(Estado estadoOrigem, Estado estadoAlcancavel, char ... simbolos) {
		for (char simbolo : simbolos)
			this.addTransicao(estadoOrigem, estadoAlcancavel, simbolo);
	}
	
	/**
	 * δ(estadoOrigem, simbolo) = estadoAlcancavel
	 */
	public void addTransicao(Estado estadoOrigem, Estado estadoAlcancavel, char simbolo) {
		TransicaoDeEstado transicao = new TransicaoDeEstado(estadoOrigem, estadoAlcancavel, simbolo);
		transicoes.add(estadoOrigem, transicao);
	}
	/**
	 * δ(estadoOrigem, Σ) = estadoAlcancavel 
	 */
	public void addTransicaoΣ(Estado estadoOrigem, Estado estadoAlcancavel, Alfabeto alfabeto) {
		for (Character simbolo : alfabeto)
			addTransicao(estadoOrigem, estadoAlcancavel, simbolo);
	}
	
	/* =========================================
	 * Aplicar
	 * =========================================*/
	/**
	 * ε-closure(q) = C
	 * 
	 * ε-closure ou fechamento-ε de um estado q é o conjunto C de
	 * todos os estados p tais que existe um caminho de q a p apenas
	 * com rótulos ε.
	 */
	public Collection<Estado> εClosure(Estado estado) {
		SetList<Estado> alcancaveis = new SetList<>();
		alcancaveis.add(estado);

		Queue<Estado> proximosAAnalisar = new LinkedList<>();
		proximosAAnalisar.add(estado);

		while (!proximosAAnalisar.isEmpty()) {
			Estado proximo = proximosAAnalisar.remove();

			for (TransicaoDeEstado transicao : transicoesεDe(proximo)) {
				boolean foiAnalisada = alcancaveis.contains(transicao.destino);

				if (!foiAnalisada)
					proximosAAnalisar.add(transicao.destino);

				alcancaveis.add(transicao.destino);
			}
		}

		return alcancaveis.list();
	}

	/**
	 * @return faz δ(estado, simbolo) quem nem AFD, desconsiderando ε-closures
	 */
	public Collection<Estado> aplicarSimples(Estado estado, Character simbolo) {
		Collection<Estado> alcancaveis = new LinkedList<>();

		transicoes.get(estado)
			.stream()
			.filter(transicao -> transicao.aceita(simbolo))
			.forEach(transicao -> alcancaveis.add(transicao.destino));

		return alcancaveis;
	}

	/* =========================================
	 * Utilitários
	 * =========================================*/
	@Override
	public Iterator<TransicaoDeEstado> iterator() {
		return transicoes.allElementos().iterator();
	}
	
	public Collection<TransicaoDeEstado> simbolosNaoPresentesEm(Alfabeto alfabeto) {
		Collection<TransicaoDeEstado> transicoes = new LinkedList<>();
		
		for (TransicaoDeEstado transicao : this)
			if (!isSimboloPresenteEmAlfabeto(alfabeto, transicao))
				transicoes.add(transicao);
		
		return transicoes;
	}

	private boolean isSimboloPresenteEmAlfabeto(Alfabeto alfabeto, TransicaoDeEstado transicao) {
		if (transicao.isε())
			return true;

		return alfabeto.contains(transicao.simbolo);
	}
	
	public Collection<Estado> estados() {
		SetList<Estado> estados = new SetList<>();
		estados.addAll(this.transicoes.getMap().keySet());

		for (TransicaoDeEstado estado : this)
			estados.add(estado.destino);

		return estados.list();
	}

	public Collection<TransicaoDeEstado> transicoesεDe(Estado estado) {
		return this.transicoes.get(estado)
			.stream()
			.filter(transicao -> transicao.isε())
			.collect(Collectors.toList());
	}

	public Collection<TransicaoDeEstado> transicoesAPartirDe(Estado estado) {
		return transicoes.get(estado);
	}
	
	@Override
	public boolean equals(Object obj) {
		FuncaoTransicao funcaoTransicao = (FuncaoTransicao) obj;
		
		return this.transicoes.equals(funcaoTransicao.transicoes);
	}
}
