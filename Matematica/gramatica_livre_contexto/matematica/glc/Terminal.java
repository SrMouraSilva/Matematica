package matematica.glc;

public class Terminal {
	private char terminal;

	public Terminal(char terminal) {
		this.terminal = terminal;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.terminal == ((Terminal) obj).terminal;
	}
	
	public char getSimbolo() {
		return terminal;
	}

	@Override
	public String toString() {
		return "" + terminal;
	}
}
