package matematica.interpolacao.polinomial.newton.tabela_diferenca;

import matematica.geral.coordenadas.Coordenada;

class FuncaoDiferencaBase implements FuncaoDiferenca {
	private Coordenada coordenada;
	
	public FuncaoDiferencaBase(Coordenada coordenada) {
		this.coordenada = coordenada;
	}

	@Override
	public Coordenada getMaiorX() {
		return this.coordenada;
	}

	@Override
	public Coordenada getMenorX() {
		return this.coordenada;
	}

	@Override
	public double valor() {
		return coordenada.y();
	}
}