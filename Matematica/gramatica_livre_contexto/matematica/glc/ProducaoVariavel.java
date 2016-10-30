package matematica.glc;

import java.util.List;

public class ProducaoVariavel extends Producao {

	private List<Variavel> corpo;

	public ProducaoVariavel(Variavel cabeca, List<Variavel> corpo) {
		super(cabeca);
		this.corpo = corpo;
	}

	@Override
	public boolean testar(Terminal terminal) {
		return false;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
	
	public Variavel getPrimeiraVariavel() {
		return this.corpo.get(0);
	}
	
	public Variavel getSegundaVariavel() {
		return this.corpo.get(1);
	}
	
	@Override
	public String toString() {
		return this.getCabeca() + " → " + this.getPrimeiraVariavel() + this.getSegundaVariavel();
	}
}
