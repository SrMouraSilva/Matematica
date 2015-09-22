package interpolacao;

import matematica.geral.coordenadas.Coordenadas;
import matematica.interpolacao.polinomial.InterpoladorPolinomial;
import matematica.interpolacao.polinomial.PolinomioInterpolador;
import matematica.interpolacao.polinomial.lagrange.LagrangeInterpolador;
import matematica.interpolacao.polinomial.newton.NewtonInterpolador;
import matematica.interpolacao.polinomial.sistema_linear.SistemaLinearInterpolador;
import matematica.sistema_linear.exception.EntradaException;

public class Interpolacao {
	public static void main(String[] args) throws EntradaException {
		double xInterpolador = 0.23;

		Coordenadas coordenadas = new Coordenadas();

		coordenadas//.add(    -1,   0)
				   .add( 0.125, 0.5)
				   .add(     2,   1);

		listemaLinear(coordenadas, xInterpolador);
		lagrange(coordenadas, xInterpolador);
		newton(coordenadas, xInterpolador);
	}

	private static void listemaLinear(Coordenadas coordenadas, double xInterpolador) throws EntradaException {
		System.out.println("====================");
		System.out.println("   Sistema Linear");
		System.out.println("====================");

		InterpoladorPolinomial interpolador = new SistemaLinearInterpolador(coordenadas);
		PolinomioInterpolador polinomio = interpolador.gerarPolinomioInterpolador();

		System.out.println(interpolador);
		System.out.println("Polinômio interpolado: P(x) =" + polinomio);
		System.out.println("Para x = " + xInterpolador + ", P(x) = " +polinomio.calcularPara(xInterpolador));
	}

	private static void lagrange(Coordenadas coordenadas, double xInterpolador) {
		System.out.println("====================");
		System.out.println("      Lagrange");
		System.out.println("====================");

		InterpoladorPolinomial lagrange = new LagrangeInterpolador(coordenadas, xInterpolador);
		//System.out.println(lagrange);
	}

	private static void newton(Coordenadas coordenadas, double xInterpolador) {
		System.out.println("====================");
		System.out.println("        Newton");
		System.out.println("====================");

		InterpoladorPolinomial newton = new NewtonInterpolador(coordenadas, xInterpolador);
		System.out.println(newton);
	}
}
