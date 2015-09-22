package matematica.interpolacao.polinomial;

import matematica.geral.Incognita;
import matematica.geral.polinomio.Polinomio;

public class PolinomioInterpolador {

    private final Polinomio polinomio;

    public PolinomioInterpolador(Polinomio polinomio) {
        this.polinomio = polinomio;
    }

    public double calcularPara(double xInterpolador) {
        Incognita x = new Incognita('x');

        return this.polinomio.atribuirValorDe(x, xInterpolador).termoIndependente().coeficiente();
    }

    public Polinomio getPolinomio() {
        return polinomio;
    }

    @Override
    public String toString() {
        return polinomio.toString();
    }
}
