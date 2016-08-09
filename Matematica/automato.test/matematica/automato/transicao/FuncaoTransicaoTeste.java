package matematica.automato.transicao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import matematica.automato.afn.Estado;
import matematica.automato.alfabeto.Alfabeto;
import matematica.automato.transicao.FuncaoTransicao;
import matematica.automato.transicao.TransicaoDeEstado;

public class FuncaoTransicaoTeste {
	
	@Test
	public void εClosureTeste() {
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);
        Estado q4 = new Estado(4);
        Estado q5 = new Estado(5);
        Estado q6 = new Estado(6);
        Estado q7 = new Estado(7);

        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q1, q2, Alfabeto.ε);
        δ.addTransicoes(q2, q3, Alfabeto.ε);
        δ.addTransicoes(q3, q6, Alfabeto.ε);
        
        δ.addTransicoes(q1, q4, Alfabeto.ε);
        δ.addTransicoes(q4, q5, Alfabeto.ε);
        δ.addTransicoes(q5, q6, Alfabeto.ε);
        δ.addTransicoes(q5, q7, Alfabeto.ε);
        
        assertEquals(Estado.estados(q1, q2, q3, q4, q5, q6, q7), sortEstados(δ.εClosure(q1)));
	}

	@Test
	public void aplicarSimplesTeste() {
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);

        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q1, q2, '0');
        δ.addTransicoes(q2, q3, '1');
        δ.addTransicoes(q2, q1, '1');
        δ.addTransicoes(q2, q2, '0');
        
        assertEquals(Estado.estados(q2), sortEstados(δ.aplicarSimples(q1, '0')));
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q1, '1')));
        
        assertEquals(Estado.estados(q2), sortEstados(δ.aplicarSimples(q2, '0')));
        assertEquals(Estado.estados(q1, q3), sortEstados(δ.aplicarSimples(q2, '1')));
        
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q3, '0')));
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q3, '1')));
        
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q1, Alfabeto.ε)));
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q2, Alfabeto.ε)));
        assertEquals(Estado.estados(), sortEstados(δ.aplicarSimples(q3, Alfabeto.ε)));
	}
	
	private Collection<Estado> sortEstados(Collection<Estado> estados) {
		Comparator<Estado> comparator = (Estado p1, Estado p2) -> new Integer(p1.index).compareTo(p2.index);
		List<Estado> sorted = new ArrayList<>(estados);
		sorted.sort(comparator);
        
        return sorted;
	}
	
	@Test
	public void addTransicaoΣTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);

        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicaoΣ(q0, q1, new Alfabeto('a', 'b', 'c'));
        
        FuncaoTransicao δ1 = new FuncaoTransicao();
        δ.addTransicoes(q0, q1, 'a', 'b', 'c');
        
        assertEquals(δ1, δ);
	}
	
	@Test
	public void transicoesεDeTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);
       
        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q0, q1, Alfabeto.ε);
        δ.addTransicao(q0, q2, '0');
        δ.addTransicao(q0, q3, Alfabeto.ε);
        
        Collection<TransicaoDeEstado> esperado = new ArrayList<>();
        esperado.add(new TransicaoDeEstado(q0, q1, Alfabeto.ε));
        esperado.add(new TransicaoDeEstado(q0, q3, Alfabeto.ε));
        
        assertEquals(esperado, δ.transicoesεDe(q0));
        assertEquals(new ArrayList<>(), δ.transicoesεDe(q1));
        assertEquals(new ArrayList<>(), δ.transicoesεDe(q2));
        assertEquals(new ArrayList<>(), δ.transicoesεDe(q3));
	}

	@Test
	public void estadosTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);
       
        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q0, q1, Alfabeto.ε);
        δ.addTransicao(q0, q2, '0');
        δ.addTransicao(q0, q3, Alfabeto.ε);
        
        assertEquals(Estado.estados(q0, q1, q2, q3), sortEstados(δ.estados()));
	}
	
	@Test
	public void transicoesAPartirDeTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);
       
        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q0, q1, Alfabeto.ε);
        δ.addTransicao(q0, q2, '0');
        δ.addTransicao(q0, q3, Alfabeto.ε);
        
        Collection<TransicaoDeEstado> esperado = new ArrayList<>();
        esperado.add(new TransicaoDeEstado(q0, q1, Alfabeto.ε));
        esperado.add(new TransicaoDeEstado(q0, q2, '0'));
        esperado.add(new TransicaoDeEstado(q0, q3, Alfabeto.ε));
        
        assertEquals(esperado, δ.transicoesAPartirDe(q0));
	}
	
	@Test
	public void simbolosNaoPresentesEmAlfabetoTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);
        Estado q2 = new Estado(2);
        Estado q3 = new Estado(3);
       
        FuncaoTransicao δ = new FuncaoTransicao();
        δ.addTransicoes(q0, q1, Alfabeto.ε);
        δ.addTransicoes(q0, q2, '0', '1', '2');
        δ.addTransicao(q0, q2, '0');
        δ.addTransicao(q0, q3, Alfabeto.ε);
        
        Collection<TransicaoDeEstado> esperado = new ArrayList<>();
        esperado.add(new TransicaoDeEstado(q0, q2, '2'));
        
        assertEquals(esperado, δ.simbolosNaoPresentesEm(new Alfabeto('0', '1')));
	}
}
