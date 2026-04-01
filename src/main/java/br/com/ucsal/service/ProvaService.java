package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.olimpiadas.Prova;
import br.com.ucsal.olimpiadas.Questao;

public class ProvaService {

	private long proximoId = 1;
	private final List<Prova> provas;
	private long proximaQuestaoId = 1;

	public ProvaService(List<Prova> provas) {
		this.provas = provas;
	}

	public Prova cadastrar(String titulo) {
		if (titulo == null || titulo.isBlank()) {
			throw new IllegalArgumentException("Título inválido");
		}

		var prova = new Prova();
		prova.setId(++proximoId);
		prova.setTitulo(titulo);

		provas.add(prova);
		return prova;
	}

	public List<Prova> listarTodas() {
		return List.copyOf(provas);
	}

	public boolean existePorId(Long id) {
		return id != null && provas.stream().anyMatch(p -> id.equals(p.getId()));
	}
	
	public void adicionarQuestao(Long provaId, String enunciado, String[] alternativas, char correta) {

	    var prova = buscarPorId(provaId);

	    if (enunciado == null || enunciado.isBlank()) {
	        throw new IllegalArgumentException("Enunciado inválido");
	    }

	    var q = new Questao();
	    q.setId(proximaQuestaoId++);
	    q.setEnunciado(enunciado);
	    q.setAlternativas(alternativas);
	    q.setAlternativaCorreta(correta);

	    prova.adicionarQuestao(q);
	}
	
	public Prova buscarPorId(Long id) {
	    return provas.stream()
	        .filter(p -> id.equals(p.getId()))
	        .findFirst()
	        .orElseThrow(() -> new IllegalArgumentException("Prova não encontrada"));
	}

}
