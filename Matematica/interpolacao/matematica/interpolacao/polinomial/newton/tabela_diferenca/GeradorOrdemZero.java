package matematica.interpolacao.polinomial.newton.tabela_diferenca;

import java.util.ArrayList;
import java.util.List;

import matematica.geral.coordenadas.Coordenada;
import matematica.geral.coordenadas.Coordenadas;

class GeradorOrdemZero {
	static Ordem gerarPara(Coordenadas coordenadas) {
		List<FuncaoDiferenca> funcoes = new ArrayList<>();

		for (Coordenada coordenada : coordenadas)
			funcoes.add(new FuncaoDiferencaBase(coordenada));

		return new Ordem(funcoes);
	}
}
