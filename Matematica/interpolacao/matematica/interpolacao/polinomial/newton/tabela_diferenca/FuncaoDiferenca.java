package matematica.interpolacao.polinomial.newton.tabela_diferenca;

import matematica.geral.coordenadas.Coordenada;

public interface FuncaoDiferenca {
	Coordenada getMaiorX();
	Coordenada getMenorX();
	
	double valor();
}
