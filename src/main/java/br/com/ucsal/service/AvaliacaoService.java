package br.com.ucsal.service;

import br.com.ucsal.olimpiadas.Tentativa;

public class AvaliacaoService {
	
	public int calcularNota(Tentativa tentativa) {
		int acertos = 0;
		for (var r : tentativa.getRespostas()) {
			if (r.isCorreta())
				acertos++;
		}
		return acertos;
	}

}
