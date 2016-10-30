package matematica.glc;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import matematica.automato.alfabeto.Alfabeto;

public abstract class Producao {
	private static final String OU = "\\|";
	private static final String SETA = "→";

	private Variavel cabeca;

	public Producao(Variavel cabeca) {
		this.cabeca = cabeca;
	}

	public Variavel getCabeca() {
		return cabeca;
	}

	public abstract boolean testar(Terminal terminal);

	public abstract boolean isTerminal();
	
	public static Collection<Producao> producoes(String ... producoes) {
		List<Producao> producoesRetorno = new LinkedList<>();

		for (String producao : producoes)
			producoesRetorno.addAll(gerarProducao(producao));

		return producoesRetorno;
	}

	private static Collection<Producao> gerarProducao(String producao) {
		Collection<Producao> producoes = new LinkedList<>();

		String[] cabecaCorpo = producao.split(SETA);

		String cabecaString = cabecaCorpo[0];
		String corpoString = cabecaCorpo[1];

		Variavel cabeca = new Variavel(cabecaString.charAt(0));

		for (String corpo : corpoString.split(OU))
			producoes.add(gerarProducao(cabeca, corpo));

		return producoes;
	}

	private static Producao gerarProducao(Variavel cabeca, String corpo) {
		if (corpo.length() == 1)
			return new ProducaoTerminal(cabeca, new Terminal(corpo.charAt(0)));
		else
			return new ProducaoVariavel(cabeca, Variavel.variaveis(corpo.toCharArray()));
	}
	
	public static Producao vazia(Variavel variavel) {
		return Producao.producoes(variavel + "→" + Alfabeto.ε).iterator().next();
	}
}
