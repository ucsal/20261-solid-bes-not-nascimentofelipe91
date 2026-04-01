package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.Resposta;
import br.com.ucsal.olimpiadas.Tentativa;

public class TentativaService {

	private long proximoId = 1;
	private final List<Tentativa> tentativas;
	private final ProvaService provaService;

	public TentativaService(List<Tentativa> tentativas, ProvaService provaService) {
		this.tentativas = tentativas;
		this.provaService = provaService;
	}

	public Tentativa aplicar(Long participanteId, Long provaId) {

		var prova = provaService.buscarPorId(provaId);
		var questoesDaProva = prova.getQuestoes();

		if (questoesDaProva.isEmpty()) {
			throw new IllegalStateException("Prova sem questões");
		}

		var tentativa = new Tentativa();
		tentativa.setId(proximoId++);
		tentativa.setParticipanteId(participanteId);
		tentativa.setProvaId(provaId);

		tentativas.add(tentativa);
		return tentativa;
	}

	public List<Tentativa> listarTodas() {
		return tentativas;
	}
	
	public void responder(Tentativa tentativa, Questao q, char marcada) {
	    var r = new Resposta();
	    r.setQuestaoId(q.getId());
	    r.setAlternativaMarcada(marcada);
	    r.setCorreta(q.isRespostaCorreta(marcada));

	    tentativa.getRespostas().add(r);
	}

}
