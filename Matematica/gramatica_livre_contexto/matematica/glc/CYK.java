package matematica.glc;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

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
							tabela.get(i, j).add(A); // NÃO TÁ ENTRANDO AQUI
					}
				}
			}
		}

		return tabela.get(1, n).contains(gramatica.getSimboloInicial());
	}

	public static class Tabela {
		private Collection<Variavel>[][] tabela;

		public Tabela(String w) {
			int n = w.length();

			tabela = new LinkedList[n+1][n+1];
		}
		
		public Collection<Variavel> get(int i, int j) {
			if (tabela[i][j] == null)
				tabela[i][j] = new LinkedList<>();
			
			return tabela[i][j];
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();

			int j = 0;
			for (Collection<Variavel>[] lists : tabela) {
				builder.append(j + " - ");

				int i = 0;
				for (Collection<Variavel> list : lists) {
					//if (i >= j)
						builder.append(Arrays.asList(list));

					i++;
				}

				builder.append('\n');
				j++;
			}

			return builder.toString();
		}
	}
}
