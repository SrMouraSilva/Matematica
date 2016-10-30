package matematica.glc;

import java.util.Collection;

import org.junit.Test;

public class ProducaoTest {

	@Test
	public void geradorProducoesTest() {
		Collection<Producao> producoes = Producao.producoes(
			"F→BE|BI|PN|p",
			"E→FG",
			"G→AH",
			"H→FC",
			"B→(",
			"A→&",
			"C→)",
			"I→DH",
			"D→∼",
			"P→p",
			"N→RN|SN|1|2",
			"R→1",
			"S→2"
		);
		
		System.out.println(producoes);
	}
}
