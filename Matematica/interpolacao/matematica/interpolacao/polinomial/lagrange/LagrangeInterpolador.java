package matematica.interpolacao.polinomial.lagrange;

import java.util.ArrayList;
import java.util.List;

import matematica.geral.coordenadas.Coordenadas;
import matematica.geral.polinomio.Polinomio;
import matematica.interpolacao.polinomial.InterpoladorPolinomial;
import matematica.interpolacao.polinomial.PolinomioInterpolador;
import matematica.interpolacao.polinomial.lagrange.polinomios.L;
import matematica.interpolacao.polinomial.lagrange.polinomios.LCalculador;
import matematica.interpolacao.polinomial.lagrange.polinomios.PolinomioLagrange;

public class LagrangeInterpolador implements InterpoladorPolinomial {

	public LagrangeInterpolador(Coordenadas coordenadas, double xInterpolador) {
		List<L> resultados = calcularLs(coordenadas, xInterpolador);
		PolinomioLagrange polinomioLagrange = new PolinomioLagrange(resultados, coordenadas);

		System.out.println("Metodo de interpolação de Lagrange!");
		System.out.println(coordenadas);
		
		System.out.println();

		for (L l : resultados) {
			System.out.println(l);
			System.out.println("\n");
		}

		System.out.println(polinomioLagrange);
		System.out.println("P"+ (resultados.size()-1) + "(x) = " + polinomioLagrange.resultado().calcularFDeX(xInterpolador));
	}

	private List<L> calcularLs(Coordenadas coordenadas, double xInterpolador) {
		List<L> resultados = new ArrayList<>();

		for (int index=0; index<coordenadas.size(); index++) {
			LCalculador calculador = new LCalculador(index);
			L resultado = calculador.calcularPara(coordenadas, xInterpolador);

			resultados.add(resultado);
		}

		return resultados;
	}

	@Override
	public PolinomioInterpolador gerarPolinomioInterpolador() {
		return null;
	}
}
