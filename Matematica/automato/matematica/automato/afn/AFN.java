package matematica.automato.afn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import matematica.automato.afn.exception.CriacaoAFNException;
import matematica.automato.afn.exception.EstadoDeTransicaoNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.EstadoFinalNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.EstadoInicialNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.SimboloNaoPresenteNoAlfabetoException;
import matematica.automato.alfabeto.Alfabeto;
import matematica.automato.alfabeto.StringConsultavel;
import matematica.automato.transicao.FuncaoTransicao;
import matematica.automato.transicao.FuncaoTransicaoPrintBuilder;
import matematica.automato.transicao.TransicaoDeEstado;
import matematica.estrututra.SetList;

/**
 * Um AFN é uma tupla (Q, Σ, δ, q0, F)
 * - Um conjunto finito de estados Q
 * - Um alfabeto de entrada Σ
 * - Uma função de transição δ
 *   - δ:Qx(ΣU{ε}) → P(Q)
 * - Um estado inicial q0 em Q
 * - Um conjunto de estado finais F ⊆ Q
 */
public class AFN {

	private Collection<Estado> estados;
	private Alfabeto alfabeto;
	private Estado estadoInicial;
	private FuncaoTransicao funcaoTransicao;
	private Collection<Estado> estadosFinais;

	public AFN(Collection<Estado> estados, Alfabeto alfabeto, FuncaoTransicao funcaoTransicao, Estado estadoInicial, Collection<Estado> estadosFinais) {
		validar(estados, alfabeto, funcaoTransicao, estadoInicial, estadosFinais);

		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.funcaoTransicao = funcaoTransicao;
		this.estadosFinais = estadosFinais;
	}

	private void validar(Collection<Estado> estados, Alfabeto alfabeto, FuncaoTransicao funcaoTransicao, Estado estadoInicial, Collection<Estado> estadosFinais) {
		if (estados.isEmpty())
			throw new CriacaoAFNException("Um autômato deve haver ao menos um estado inicial");

		if (!estados.contains(estadoInicial))
			throw new EstadoInicialNaoPresenteNosEstadosException("Estado inicial deve ser um dos estados do AFN");
		
		if (!estados.containsAll(estadosFinais))
			throw new EstadoFinalNaoPresenteNosEstadosException("Todos os estados finais devem estar contidos nos estados do AFN");

		Collection<TransicaoDeEstado> simbolosNaoPresentes = funcaoTransicao.simbolosNaoPresentesEm(alfabeto);
		
		if (!simbolosNaoPresentes.isEmpty())
			throw new SimboloNaoPresenteNoAlfabetoException("Definida um ou mais transições onde seu símbolo não está presente no alfabeto: " + simbolosNaoPresentes);

		if (!estados.containsAll(funcaoTransicao.estados()))
			throw new EstadoDeTransicaoNaoPresenteNosEstadosException("Todos os estados em transições devem estar contidos nos estados do AFN");
	}

	/**
	 * Verifica se a string é aceita pelo AFN
	 * 
	 * @return δ*(q0, w) ∩ F ≠ ∅
	 */
	public boolean aceita(String string) {
		return this.aceita(new StringConsultavel(string), this.estadoInicial);
	}
	
	private boolean aceita(StringConsultavel string, Estado estado) {
		Collection<Estado> estados = new ArrayList<>();
		estados.add(estado);
		
		while (true) {
			Collection<Estado> estadosAtuais = εClosureMultiplo(estados);

			if (string.isTodaProcessada())
				return isAlgumEstadoFinal(estadosAtuais);

			Character simbolo = string.proximoSimbolo().get();

			Collection<Estado> estadosAlcancaveis = new LinkedList<>();
	
			for (Estado estadoAtual : estadosAtuais)
				estadosAlcancaveis.addAll(funcaoTransicao.aplicarSimples(estadoAtual, simbolo));

			estados = estadosAlcancaveis;
		}
	}

	/**
	 * @return Aplica ε-closure para todos os estados passados
	 */
	private Collection<Estado> εClosureMultiplo(Collection<Estado> estados) {
		SetList<Estado> retorno = new SetList<>();
		
		for (Estado estado : estados)
			retorno.addAll(funcaoTransicao.εClosure(estado));

		return retorno.list();
	}

	private boolean isAlgumEstadoFinal(Collection<Estado> estados) {
		return !Collections.disjoint(estados, estadosFinais);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("O automato gerado é da forma:\n");
		builder.append("   A = (Q, Σ, δ, qi, F) \n");
		builder.append("tal que\n");
		builder.append(" - Q = ").append(estados).append("\n");
		builder.append(" - Σ = ").append(alfabeto).append("\n");
		builder.append(" - qi = ").append(estadoInicial).append("\n");
		builder.append(" - F = ").append(estadosFinais).append("\n");
		
		builder.append(" - δ definida como:\n");
		builder.append(FuncaoTransicaoPrintBuilder.build(alfabeto, funcaoTransicao));

		return builder.toString();
	}
}
