package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.olimpiadas.Prova;

public class ProvaService {

	private long proximoId = 1;
	private final List<Prova> provas;

	public ProvaService(List<Prova> provas) {
		this.provas = provas;
	}

	public Prova cadastrar(String titulo) {
		if (titulo == null || titulo.isBlank()) {
			throw new IllegalArgumentException("Título inválido");
		}

		var prova = new Prova();
		prova.setId(proximoId++);
		prova.setTitulo(titulo);

		provas.add(prova);
		return prova;
	}

}
