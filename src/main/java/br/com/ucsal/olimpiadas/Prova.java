package br.com.ucsal.olimpiadas;

import java.util.ArrayList;
import java.util.List;

public class Prova {

	private long id;
	private String titulo;
	private List<Questao> questoes = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void adicionarQuestao(Questao questao) {
		questoes.add(questao);
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

}
