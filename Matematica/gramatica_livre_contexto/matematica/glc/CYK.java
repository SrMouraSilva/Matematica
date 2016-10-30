package matematica.glc;

import java.util.ArrayList;
import java.util.List;

import matematica.automato.alfabeto.Alfabeto;

public class CYK {
	public static boolean testar(GramaticaLivreDeContexto gramatica, String w) {
		int n = w.length();
		
		if (n == 0)
			return gramatica.testarRegra(gramatica.getSimboloInicial(), Alfabeto.ε);

		Tabela tabela = new Tabela(w);
		
		for (int i=1; i<=n; i++) {
			char chari = w.charAt(i-1);

			for (Variavel A : gramatica.getVariaveis())
				if (gramatica.testarRegra(A, chari))
					tabela.get(i, i).add(A);
		}
		
		for (int l=2; l<=n; l++) {
			for (int i=1; i<=n-l+1; i++) {
				int j = i + l - 1;

				for (int k=i; k<=j-1; k++) {
					// each rule A -> BC
					for (ProducaoVariavel producao : gramatica.getProducoesVariaveis()) {
						Variavel A = producao.getCabeca();
						Variavel B = producao.getPrimeiraVariavel();
						Variavel C = producao.getSegundaVariavel();

						if (tabela.get(i, k).contains(B) && tabela.get(k+1, j).contains(C))
							tabela.get(i, j).add(A);
					}
				}
			}
		}

		return tabela.get(1, n).contains(gramatica.getSimboloInicial());
	}

	public static class Tabela {
		private List<Variavel>[][] tabela;

		public Tabela(String w) {
			int n = w.length();

			tabela = new List[n+1][n+1];
		}
		
		public List<Variavel> get(int i, int j) {
			if (tabela[i][i] == null)
				tabela[i][i] = new ArrayList<>();
			
			return tabela[i][i];
		}
	}
}
