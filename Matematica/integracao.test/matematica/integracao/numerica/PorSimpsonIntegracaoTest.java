package matematica.integracao.numerica;

import matematica.geral.Intervalo;
import matematica.integracao.Funcao;
import matematica.integracao.Integracao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PorSimpsonIntegracaoTest {
	@Test
	public void calcularParaTest() {
		Funcao funcao         = (x) -> 1/(x*x);
		Intervalo intervalo   = new Intervalo(1, 7);
		int numeroDeParticoes = 10;

		Integracao integracao = new PorSimpsonIntegracao();
		assertEquals(0.8657804001232479, integracao.calcularPara(funcao, intervalo, numeroDeParticoes), 0);
	}
}
