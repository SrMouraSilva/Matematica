package matematica.glc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import matematica.glc.exception.SemProducoesException;

public class GramaticaLivreDeContextoTest {

	@Test(expected=SemProducoesException.class)
	public void nenhumaProducaoTest() {
		new GramaticaLivreDeContexto(new ArrayList<>());
	}
	
	@Test
	public void aceitaVazioTest() {
		Collection<Producao> producoes = new ArrayList<>();
		producoes.add(Producao.vazia(new Variavel('E')));

		GramaticaLivreDeContexto gramatica = new GramaticaLivreDeContexto(producoes);

		assertTrue(CYK.testar(gramatica, ""));
	}

	@Test
	public void naoAceitaVazioTest() {
		Collection<Producao> producoes = Producao.producoes("E→a");

		GramaticaLivreDeContexto gramatica = new GramaticaLivreDeContexto(producoes);

		assertFalse(CYK.testar(gramatica, ""));
	}

	/**
	 * F → (F&F)
	 * F → (~ F)
	 * F → pN
	 * N → 1N | 2N | ε
	 */
	@Test
	public void trabalhoTest() {
		Collection<Producao> producoes = Producao.producoes(
			"F→BE|BI|PN|p",
			"E→FG",
			"G→AH",
			"H→FC",
			"B→(",
			"A→&",
			"C→)",
			"I→DH",
			"D→~",
			"P→p",
			"N→RN|SN|1|2",
			"R→1",
			"S→2"
		);

		GramaticaLivreDeContexto gramatica = new GramaticaLivreDeContexto(producoes);
		
		//assertTrue(CYK.testar(gramatica, "p"));
		assertTrue(CYK.testar(gramatica, "p1"));
		assertTrue(CYK.testar(gramatica, "p11"));
		assertTrue(CYK.testar(gramatica, "p12"));
		assertTrue(CYK.testar(gramatica, "p2"));
		assertTrue(CYK.testar(gramatica, "p22"));
		assertTrue(CYK.testar(gramatica, "p12"));
		assertTrue(CYK.testar(gramatica, "p121212112"));
		
		assertTrue(CYK.testar(gramatica, "(p&p)"));
		assertTrue(CYK.testar(gramatica, "(~p)"));

		assertTrue(CYK.testar(gramatica, "((~p11)&(p121212&p))"));
		
		
		assertFalse(CYK.testar(gramatica, ""));
		assertFalse(CYK.testar(gramatica, "a"));
		assertFalse(CYK.testar(gramatica, "abba"));
		assertFalse(CYK.testar(gramatica, "p."));

		assertFalse(CYK.testar(gramatica, "(( ))"));
		assertFalse(CYK.testar(gramatica, "(p&pa)"));
		assertFalse(CYK.testar(gramatica, "(p&p.)"));
		
		assertFalse(CYK.testar(gramatica, "(~.)"));

		assertFalse(CYK.testar(gramatica, "((~p11)&(p121212&p.))"));
	}
}
