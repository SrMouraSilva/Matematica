package matematica.automato.afn.buscador;

import static org.junit.Assert.*;

import org.junit.Test;

import matematica.automato.afn.buscador.Buscador;

public class BuscadorTeste {

	@Test
	public void buscaVaziaTextoVazioTest() {
		String texto = "";
		
		assertTrue(Buscador.buscar(texto, ""));
	}

	@Test
	public void buscaVaziaTextoPresenteTest() {
		String texto = "Comer tatu é bom, que pena que dá dor nas costas Porque o bicho é baixinho, e é por isso que eu prefiro as cabritas As cabrita têm seios, que alimentam os seus descendentes";
		assertTrue(Buscador.buscar(texto, ""));
	}
	
	@Test
	public void buscaTextoInicioTest() {
		String texto = "Comer tatu é bom, que pena que dá dor nas costas Porque o bicho é baixinho, e é por isso que eu prefiro as cabritas As cabrita têm seios, que alimentam os seus descendentes";
		assertTrue(Buscador.buscar(texto, "Comer "));
	}
	
	@Test
	public void buscaTextoFimTest() {
		String texto = "Comer tatu é bom, que pena que dá dor nas costas Porque o bicho é baixinho, e é por isso que eu prefiro as cabritas As cabrita têm seios, que alimentam os seus descendentes";
		assertTrue(Buscador.buscar(texto, " descendentes"));
	}
	
	@Test
	public void buscaTextoInexistenteTest() {
		String texto = "Comer tatu é bom, que pena que dá dor nas costas Porque o bicho é baixinho, e é por isso que eu prefiro as cabritas As cabrita têm seios, que alimentam os seus descendentes";
		assertFalse(Buscador.buscar(texto, "comer"));
		assertFalse(Buscador.buscar(texto, "TaTu"));
		assertFalse(Buscador.buscar(texto, " eh "));
	}
	
	@Test
	public void buscaTextoInexistenteRepetidoTest() {
		String texto = "Comer tatu é bom, que pena que dá dor nas costas Porque o bicho é baixinho, e é por isso que eu prefiro as cabritas As cabrita têm seios, que alimentam os seus descendentes";
		assertFalse(Buscador.buscar(texto, "tatu tatu"));
	}
	
	@Test
	public void buscaMaiorQueTextoTest() {
		String texto = "Comer";
		assertFalse(Buscador.buscar(texto, "Commmmmmmmmmmmmmmmmmmmmmeeeeeeeeeeeeerrrr"));
	}

}
