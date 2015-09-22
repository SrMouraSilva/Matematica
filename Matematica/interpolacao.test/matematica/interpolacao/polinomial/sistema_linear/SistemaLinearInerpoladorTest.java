package matematica.interpolacao.polinomial.sistema_linear;

import matematica.geral.Incognita;
import matematica.geral.coordenadas.Coordenadas;
import matematica.geral.polinomio.Monomio;
import matematica.geral.polinomio.ParteLiteral;
import matematica.geral.polinomio.Polinomio;
import matematica.interpolacao.polinomial.InterpoladorPolinomial;
import matematica.interpolacao.polinomial.PolinomioInterpolador;
import matematica.sistema_linear.exception.EntradaException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SistemaLinearInerpoladorTest {

	public InterpoladorPolinomial interpolador;

	@Before
	public void before() throws EntradaException {
		Coordenadas coordenadas = new Coordenadas();

		coordenadas.add( 0.125, 0.5)
				   .add(     2,   1);

		this.interpolador = new SistemaLinearInterpolador(coordenadas);
	}

	@Test
	public void gerarPolinomioInterpoladorTest() throws EntradaException {
		Incognita x0 = new Incognita('x', 0);
		Incognita x1 = new Incognita('x', 1);

		//+ 3.75x^1.0  - 1.75x^0.0
		Polinomio esperado = Polinomio.nulo()
				.mais(new Monomio(3.75, ParteLiteral.com(x1)))
				.menos(new Monomio(1.75, ParteLiteral.com(x0)));

		assertEquals(esperado, interpolador.gerarPolinomioInterpolador().getPolinomio());
	}

	@Test
	public void gerarYInterpolado() throws EntradaException {
		PolinomioInterpolador polinomio = interpolador.gerarPolinomioInterpolador();

		assertEquals(-0.8875, polinomio.calcularPara(0.23), 0);
	}

}
