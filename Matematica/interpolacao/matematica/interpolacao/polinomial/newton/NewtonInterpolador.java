package matematica.interpolacao.polinomial.newton;

import matematica.geral.polinomio.Polinomio;
import matematica.interpolacao.polinomial.InterpoladorPolinomial;
import matematica.interpolacao.polinomial.PolinomioInterpolador;
import matematica.interpolacao.polinomial.newton.tabela_diferenca.TabelaDeDiferencasDivididas;
import utilitarios.StringBuilderLn;
import matematica.geral.coordenadas.Coordenadas;
import matematica.geral.polinomio.OldPolinomio;

public class NewtonInterpolador implements InterpoladorPolinomial {

	private double xInterpolador;

	private Coordenadas coordenadas;

	private TabelaDeDiferencasDivididas tabela;
	private DiferencasDivididasImportantes diferencasImportantes;

	private OldPolinomio polinomio;

	public NewtonInterpolador(Coordenadas coordenadas, double xInterpolador) {
		this.xInterpolador = xInterpolador;

		this.coordenadas = coordenadas;
		this.tabela = new TabelaDeDiferencasDivididas(coordenadas);

		this.diferencasImportantes = new DiferencasDivididasImportantes(tabela, coordenadas);

		this.polinomio = GerarPolinomioDeNewton.gerar(diferencasImportantes, coordenadas);
	}

	@Override
	public String toString() {
		StringBuilderLn builder = new StringBuilderLn();
		builder.append(coordenadas.toString());
		builder.append("\n\n");
		
		builder.appendLn(" - Equa��o Polinomial na forma de Newton:");
		builder.appendLn(equacaoPolinomial());

		builder.appendLn();
		builder.appendLn("Onde");

		builder.appendLn(detalhesEquacaoPolinomial());
		builder.appendLn();

		builder.appendLn(" - Tabela de Diferen�as Divididas");
		builder.appendLn(tabela.toString());
		
		builder.appendLn(diferencasImportantes);

		builder.appendLn(" - Equa��o Polinomial na forma de Newton:");
		builder.appendLn(equacaoPolinomial());
		builder.appendLn(equacaoPolinomialComFN());
		builder.append("P"+(coordenadas.size()-1)+"(x) =");
		builder.appendLn(polinomio);

		builder.appendLn();
		builder.append("P"+(coordenadas.size()-1)+"(x) = ");
		builder.appendLn(polinomio.calcularFDeX(xInterpolador));
		
		return builder.toString();
	}

	private StringBuilderLn equacaoPolinomial() {
		StringBuilderLn builder = new StringBuilderLn();

		builder.append("P"+(coordenadas.size()-1)+"(x) = ");
		for (int i = 0; i < coordenadas.size(); i++) {
			builder.append(" + d"+i);

			for (int j = 0; j < i; j++)
				builder.append("(x - x"+j+")");
		}
		
		return builder;
	}
	
	private Object detalhesEquacaoPolinomial() {
		StringBuilderLn builder = new StringBuilderLn();

		for (int i = 0; i < coordenadas.size(); i++) {
			builder.append("d"+i+" = f[");

			for (int j = 0; j < i+1; j++)
				builder.append("x"+j+",");

			builder.appendLn("]");
		}

		return builder;
	}

	private StringBuilderLn equacaoPolinomialComFN() {
		StringBuilderLn builder = new StringBuilderLn();

		builder.append("P"+(coordenadas.size()-1)+"(x) = ");
		for (int i = 0; i < coordenadas.size(); i++) {
			builder.append(" + f[");

			for (int j = 0; j < i+1; j++)
				builder.append("x"+j+",");

			builder.append("]");

			for (int j = 0; j < i; j++)
				builder.append("(x - x"+j+")");
		}
		
		return builder;
	}

	@Override
	public PolinomioInterpolador gerarPolinomioInterpolador() {
		return null;
	}
}
