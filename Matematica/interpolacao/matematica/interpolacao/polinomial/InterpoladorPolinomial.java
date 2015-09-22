package matematica.interpolacao.polinomial;

import matematica.interpolacao.Interpolador;

public interface InterpoladorPolinomial extends Interpolador {
	PolinomioInterpolador gerarPolinomioInterpolador();
}