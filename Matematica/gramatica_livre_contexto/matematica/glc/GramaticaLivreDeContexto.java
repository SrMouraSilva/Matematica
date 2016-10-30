package matematica.glc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import matematica.glc.exception.SemProducoesException;

public class GramaticaLivreDeContexto {
	private Collection<Variavel> variaveis;
	private Collection<Terminal> terminais;
	private Collection<Producao> producoes;
	private Variavel simboloInicial;

	public GramaticaLivreDeContexto(Collection<Producao> producoes) {
		if (producoes.size() == 0)
			throw new SemProducoesException("Foi passado uma lista sem produções");

		this.producoes = producoes;

		this.variaveis = gerarVariaveis(producoes);
		this.terminais = gerarTerminais(getProducoesTerminais());
		
		this.simboloInicial = producoes.iterator().next().getCabeca();
	}

	private Collection<Variavel> gerarVariaveis(Collection<Producao> producoes) {
		Set<Variavel> variaveis = new HashSet<>();

		for (Producao producao : producoes)
			variaveis.add(producao.getCabeca());

		return variaveis;
	}

	private Collection<Terminal> gerarTerminais(Collection<ProducaoTerminal> producoes) {
		Set<Terminal> terminais = new HashSet<>();

		for (ProducaoTerminal producao : producoes)
			terminais.add(producao.getTerminal());

		return terminais;
	}

	public Collection<Variavel> getVariaveis() {
		return variaveis;
	}

	public boolean testarRegra(Variavel variavel, char caractere) {
		for (Producao producao : getProducoesDaVariavel(variavel))
			if (producao.testar(new Terminal(caractere)))
				return true;

		return false;
	}

	public Collection<Producao> getProducoesDaVariavel(Variavel variavel) {
		return (Collection<Producao>) filter(producoes, producao -> producao.getCabeca().equals(variavel));
	}

	public Collection<ProducaoVariavel> getProducoesVariaveis() {
		return (Collection<ProducaoVariavel>) filter(producoes, producao -> !producao.isTerminal());
	}

	public Collection<ProducaoTerminal> getProducoesTerminais() {
		return (Collection<ProducaoTerminal>) filter(producoes, producao -> producao.isTerminal());
	}

	private <T> Collection<? extends T> filter(Collection<T> producoes, Predicate<T> filter) {
		return producoes.stream()
						.filter(filter)
						.collect(Collectors.toList());
	}

	public Variavel getSimboloInicial() {
		return simboloInicial;
	}
}
