package matematica.automato.alfabeto;

import java.util.Optional;

public class StringConsultavel {
	private String string;
	
	private char[] simbolos;
	private int ponteiro = 0;

	public StringConsultavel(String string) {
		this.string = string;
		this.simbolos = string.toCharArray();
	}

	public Optional<Character> proximoSimbolo() {
		if (isTodaProcessada())
			return Optional.empty();
		
		Optional<Character> simbolo = Optional.of(simbolos[ponteiro]);
		ponteiro += 1;

		return simbolo;
	}
	
	public boolean isTodaProcessada() {
		return ponteiro == simbolos.length;
	}
	
	@Override
	public String toString() {
		return this.string;
	}
}
