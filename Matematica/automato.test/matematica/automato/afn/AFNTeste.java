package matematica.automato.afn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import matematica.automato.afn.AFN;
import matematica.automato.afn.Estado;
import matematica.automato.afn.exception.CriacaoAFNException;
import matematica.automato.afn.exception.EstadoDeTransicaoNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.EstadoFinalNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.EstadoInicialNaoPresenteNosEstadosException;
import matematica.automato.afn.exception.SimboloNaoPresenteNoAlfabetoException;
import matematica.automato.alfabeto.Alfabeto;
import matematica.automato.transicao.FuncaoTransicao;

public class AFNTeste {

	@Test(expected=CriacaoAFNException.class)
	public void estadosVaziosTest() {
		Estado inicial = new Estado(0);
		
		Collection<Estado> estadosVazio = Estado.estados();
		
		new AFN(estadosVazio, new Alfabeto('a'), new FuncaoTransicao(), inicial, estadosVazio);
	}

	@Test(expected=EstadoInicialNaoPresenteNosEstadosException.class)
	public void estadoInicialForaDoEstadosTest() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		
		Estado inicial = new Estado(2);
		
		Collection<Estado> estados = Estado.estados(q0, q1);
		
		new AFN(estados, new Alfabeto('a', 'b'), new FuncaoTransicao(), inicial, estados);
	}
	
	@Test(expected=EstadoFinalNaoPresenteNosEstadosException.class)
	public void estadoFinalForaDoEstadosTest() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		Estado q2 = new Estado(2);
		
		Estado q3 = new Estado(3);
		
		Collection<Estado> estados = Estado.estados(q0, q1, q2);
		Collection<Estado> estadosFinais = Estado.estados(q1, q3);
		
		new AFN(estados, new Alfabeto('a', 'b'), new FuncaoTransicao(), q2, estadosFinais);
	}
	
	@Test(expected=SimboloNaoPresenteNoAlfabetoException.class)
	public void simboloNaoPresenteNoAlfabetoTest() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		Estado q2 = new Estado(2);
		
		Alfabeto alfabeto = new Alfabeto('a', 'b');
		Collection<Estado> estados = Estado.estados(q0, q1, q2);
		Collection<Estado> estadosFinais = Estado.estados(q1);
		
		FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
		funcaoTransicao.addTransicoes(q0, q1, 'a');
		funcaoTransicao.addTransicoes(q1, q2, 'b');
		funcaoTransicao.addTransicoes(q2, q1, 'c');
		
		new AFN(estados, alfabeto, funcaoTransicao, q2, estadosFinais);
	}
	
	@Test(expected=EstadoDeTransicaoNaoPresenteNosEstadosException.class)
	public void estadoDestinoNaoPresenteNosEstadosTest() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		
		Alfabeto alfabeto = new Alfabeto('a', 'b');
		
		Collection<Estado> estados = Estado.estados(q0);
		
		FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
		funcaoTransicao.addTransicoes(q0, q1, 'a');
		
		new AFN(estados, alfabeto, funcaoTransicao, q0, estados);
	}
	
	@Test(expected=EstadoDeTransicaoNaoPresenteNosEstadosException.class)
	public void estadoOrigemNaoPresenteNosEstadosTest() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		
		Alfabeto alfabeto = new Alfabeto('a', 'b');
		
		Collection<Estado> estados = Estado.estados(q0);
		
		FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
		funcaoTransicao.addTransicoes(q1, q0, 'a');

		new AFN(estados, alfabeto, funcaoTransicao, q0, estados);
	}

	/**
	 * L(N1) = {xyz ∈ {0,1}* | x ∈ {0,1}*, z ∈ {0,1}* e y = 11 ou y = 101}
	 */
	@Test
	public void exemploUmTeste() {
		Estado q1 = new Estado(1);
		Estado q2 = new Estado(2);
		Estado q3 = new Estado(3);
		Estado q4 = new Estado(4);
		
		Alfabeto alfabeto = new Alfabeto('0', '1');
		
		FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
		funcaoTransicao.addTransicoes(q1, q1, '0', '1');
		funcaoTransicao.addTransicao(q1, q2, '1');
		funcaoTransicao.addTransicoes(q2, q3, '0', Alfabeto.ε);
		funcaoTransicao.addTransicao(q3, q4, '1');
		funcaoTransicao.addTransicoes(q4, q4, '0', '1');


		Collection<Estado> estados = Estado.estados(q1, q2, q3, q4);
		Collection<Estado> estadosFinais = Estado.estados(q4);
		
		AFN afn = new AFN(estados, alfabeto, funcaoTransicao, q1, estadosFinais);

		assertFalse(afn.aceita(""));
		assertFalse(afn.aceita("0"));
		assertFalse(afn.aceita("00"));
		assertFalse(afn.aceita("010"));
		assertFalse(afn.aceita("1001"));
		
		assertTrue(afn.aceita("11"));
		assertTrue(afn.aceita("101"));
		assertTrue(afn.aceita("0010100"));
		assertTrue(afn.aceita("001100"));
		assertTrue(afn.aceita("01010101"));
	}
	
	@Test
	public void exemploDoisTeste() {
		Estado q0 = new Estado(0);
		Estado q1 = new Estado(1);
		Estado q2 = new Estado(2);
		Estado q3 = new Estado(3);
		Estado q4 = new Estado(4);
		Estado q5 = new Estado(5);
		Estado q6 = new Estado(6);
		Estado q7 = new Estado(7);
		Estado q8 = new Estado(8);
		Estado q9 = new Estado(9);
		Estado q10 = new Estado(10);
		
		Alfabeto alfabeto = new Alfabeto('a', 'b');
		
		FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
		funcaoTransicao.addTransicoes(q0, q1, Alfabeto.ε);
		funcaoTransicao.addTransicoes(q0, q7, Alfabeto.ε);
		
		funcaoTransicao.addTransicoes(q1, q2, Alfabeto.ε);
		funcaoTransicao.addTransicoes(q1, q4, Alfabeto.ε);
		funcaoTransicao.addTransicoes(q2, q3, 'a');
		funcaoTransicao.addTransicoes(q3, q6, Alfabeto.ε);
		funcaoTransicao.addTransicoes(q4, q5, 'b');
		funcaoTransicao.addTransicoes(q5, q6, Alfabeto.ε);
		
		funcaoTransicao.addTransicoes(q6, q7, Alfabeto.ε);
		funcaoTransicao.addTransicoes(q6, q1, Alfabeto.ε);
		
		funcaoTransicao.addTransicoes(q7, q8, 'a');
		funcaoTransicao.addTransicoes(q8, q9, 'b');
		funcaoTransicao.addTransicoes(q9, q10, 'b');

		Collection<Estado> estados = Estado.estados(q0, q1, q2, q3, q4, q5, q6, q7, q8, q9, q10);
		Collection<Estado> estadosFinais = Estado.estados(q10);

		AFN afn = new AFN(estados, alfabeto, funcaoTransicao, q0, estadosFinais);
		
		assertFalse(afn.aceita(""));
		assertFalse(afn.aceita("aba"));
		assertFalse(afn.aceita("abba"));
		assertFalse(afn.aceita("abbb"));
		assertFalse(afn.aceita("babaabba"));
		assertFalse(afn.aceita("babababa"));
		assertFalse(afn.aceita("ababababa"));
		assertFalse(afn.aceita("bababbab"));
		assertFalse(afn.aceita("bbbbbbaaaaaa"));
		assertFalse(afn.aceita("aabba"));
		assertFalse(afn.aceita("ababbab"));

		assertTrue(afn.aceita("abb"));
		assertTrue(afn.aceita("aabb"));
		assertTrue(afn.aceita("babb"));
		assertTrue(afn.aceita("bbbbabb"));
		assertTrue(afn.aceita("babababb"));
		assertTrue(afn.aceita("abaabb"));
		assertTrue(afn.aceita("bbbbababb"));
	}

	@Test
	public void estadosFinaisTest() {
		Estado inicial = new Estado(0);
		Estado estadoFinal = new Estado(1);
		
		Collection<Estado> estados = Estado.estados(inicial, estadoFinal);
		Collection<Estado> estadosFinais = Estado.estados(estadoFinal);
		
		FuncaoTransicao funcao = new FuncaoTransicao();
		funcao.addTransicoes(inicial, estadoFinal, '0');

		AFN afn = new AFN(estados, new Alfabeto('0'), funcao, inicial, estadosFinais);
		
		assertFalse(afn.aceita(""));
		assertFalse(afn.aceita("00000"));
		
		assertTrue(afn.aceita("0"));
	}
	
	/**
	 * https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/NFAexample.svg/300px-NFAexample.svg.png 
	 */
	@Test
    public void exemploTresTeste() {
        Estado s0 = new Estado(0);
        Estado s1 = new Estado(1);
        Estado s2 = new Estado(2);
        Estado s3 = new Estado(3);
        Estado s4 = new Estado(4);
       
        Alfabeto alfabeto = new Alfabeto('0', '1');
       
        FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
        funcaoTransicao.addTransicoes(s0, s1, Alfabeto.ε);
        funcaoTransicao.addTransicao(s0, s3, Alfabeto.ε);
        
        funcaoTransicao.addTransicoes(s1, s1,'1');
        funcaoTransicao.addTransicoes(s1, s2,'0');
        funcaoTransicao.addTransicoes(s2, s2,'1');
        funcaoTransicao.addTransicoes(s2, s1,'0');
        
        funcaoTransicao.addTransicoes(s3, s3,'0');
        funcaoTransicao.addTransicoes(s3, s4,'1');
        funcaoTransicao.addTransicoes(s4, s4,'0');
        funcaoTransicao.addTransicoes(s4, s3,'1');

        Collection<Estado> estados = new ArrayList<>();
        estados.add(s0);
        estados.add(s1);
        estados.add(s2);
        estados.add(s3);
        estados.add(s4);
       
        Collection<Estado> estadosFinais = new ArrayList<>();
        estadosFinais.add(s1);
        estadosFinais.add(s3);
       
        AFN afn = new AFN(estados, alfabeto, funcaoTransicao, s0, estadosFinais);

        assertTrue(afn.aceita(""));
        assertTrue(afn.aceita("0"));
        assertTrue(afn.aceita("1"));
        
        assertFalse(afn.aceita("01"));
        assertFalse(afn.aceita("10"));
        
        assertTrue(afn.aceita("11"));
        assertTrue(afn.aceita("000"));
        assertTrue(afn.aceita("001"));
        assertTrue(afn.aceita("010"));
        assertTrue(afn.aceita("011"));
        assertTrue(afn.aceita("100"));
        assertTrue(afn.aceita("101"));
        assertTrue(afn.aceita("110"));
        assertTrue(afn.aceita("111"));
        
        assertFalse(afn.aceita("010101"));
        assertFalse(afn.aceita("101010"));
	}
	/**
	 *	https://dl.dropboxusercontent.com/u/4914805/disciplinas/2016-1/tcomp/aulas/Aula%2004%20-%20Aut%C3%B4matos%20Finitos%20N%C3%A3o%20Determin%C3%ADsticos.pdf
	 *	página 23
	 *	OBS : modificado 	-> 	estados finais : s6
	 *						-> 	estado inicial : s1
	 */
	@Test
	public void exemploQuatroTeste() {
        Estado s1 = new Estado(1);
        Estado s2 = new Estado(2);
        Estado s3 = new Estado(3);
        Estado s4 = new Estado(4);
        Estado s5 = new Estado(5);
        Estado s6 = new Estado(6);
        Estado s7 = new Estado(7);
        
        Alfabeto alfabeto = new Alfabeto('a', 'b');
        
        FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
        funcaoTransicao.addTransicoes(s1, s2,Alfabeto.ε);
        funcaoTransicao.addTransicao(s1, s4, Alfabeto.ε);
        
        funcaoTransicao.addTransicoes(s2, s3,Alfabeto.ε);
        
        funcaoTransicao.addTransicoes(s3, s6,Alfabeto.ε);
        
        funcaoTransicao.addTransicoes(s4, s5,'a');
        
        funcaoTransicao.addTransicoes(s5, s6,'b');
        funcaoTransicao.addTransicoes(s5, s7,Alfabeto.ε);
        
        Collection<Estado> estados = new ArrayList<>();
        estados.add(s1);
        estados.add(s2);
        estados.add(s3);
        estados.add(s4);
        estados.add(s5);
        estados.add(s6);
        estados.add(s7);
        
        Collection<Estado> estadosFinais = new ArrayList<>();
        estadosFinais.add(s6);
        
        AFN afn = new AFN(estados, alfabeto, funcaoTransicao, s1, estadosFinais);
        
        assertTrue(afn.aceita(""));
        assertFalse(afn.aceita("a"));
        assertFalse(afn.aceita("b"));
        assertFalse(afn.aceita("aa"));
        assertTrue(afn.aceita("ab"));
        assertFalse(afn.aceita("ba"));
        assertFalse(afn.aceita("bb"));

	}
	/**
	 *	https://sites.google.com/site/b36269/_/rsrc/1447719896221/automatas/resumenes/automatas/Screen%20Shot%202015-11-16%20at%206.24.46%20PM.png?height=183&width=320
	 *	estado inicial : q0
	 */
	@Test
	public void exemploCincoTeste() {
		Estado q0 = new Estado(1);
        Estado q1 = new Estado(2);
        
        Alfabeto alfabeto = new Alfabeto('1');
        
        FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
        funcaoTransicao.addTransicoes(q0, q1,'1');
        funcaoTransicao.addTransicao(q1, q0, Alfabeto.ε);
        
        Collection<Estado> estados = new ArrayList<>();
        estados.add(q0);
        estados.add(q1);
        
        Collection<Estado> estadosFinais = new ArrayList<>();
        estadosFinais.add(q1);
        
        AFN afn = new AFN(estados, alfabeto, funcaoTransicao, q0, estadosFinais);

        assertFalse(afn.aceita(""));
        assertTrue(afn.aceita("1"));
        assertTrue(afn.aceita("11"));
        assertTrue(afn.aceita("111"));
        assertTrue(afn.aceita("1111"));
        
	}
	/**
	 *	https://sites.google.com/site/b36269/_/rsrc/1447719896221/automatas/resumenes/automatas/Screen%20Shot%202015-11-16%20at%206.24.46%20PM.png?height=183&width=320	
	 *	OBS : modificado	->	estado inicial : q0
	 *							substitui δ(q0, 1) = q1 por δ(q0, ε) = q1
	 *							adiciona-se a transição δ(q0, 1) = q0
	 */
	@Test
	public void exemploSeisTeste() {
		Estado q0 = new Estado(0);
        Estado q1 = new Estado(1);
        
        Alfabeto alfabeto = new Alfabeto('1');
        
        FuncaoTransicao funcaoTransicao = new FuncaoTransicao();
        funcaoTransicao.addTransicoes(q0, q1, Alfabeto.ε);
        funcaoTransicao.addTransicoes(q0, q0,'1');
        funcaoTransicao.addTransicao(q1, q0, Alfabeto.ε);
        
        Collection<Estado> estados = new ArrayList<>();
        estados.add(q0);
        estados.add(q1);
        
        Collection<Estado> estadosFinais = new ArrayList<>();
        estadosFinais.add(q1);
        
        AFN afn = new AFN(estados, alfabeto, funcaoTransicao, q0, estadosFinais);
       
        assertTrue(afn.aceita(""));
        assertTrue(afn.aceita("1"));
        assertTrue(afn.aceita("11"));
        assertTrue(afn.aceita("111"));
        assertTrue(afn.aceita("1111"));
        
	}
	
}
