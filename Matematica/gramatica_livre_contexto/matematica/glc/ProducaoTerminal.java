package matematica.glc;

public class ProducaoTerminal extends Producao {
	
	private Terminal corpo;

	public ProducaoTerminal(Variavel cabeca, Terminal corpo) {
		super(cabeca);
		this.corpo = corpo;
	}
	
	public Terminal getTerminal() {
		return corpo;
	}

	@Override
	public boolean testar(Terminal terminal) {
		return this.corpo.equals(terminal);
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
	
	@Override
	public String toString() {
		return this.getCabeca() + " → " + this.getTerminal();
	}
}
