package br.com.ucsal.service;

import java.util.List;

import br.com.ucsal.olimpiadas.Questao;
import br.com.ucsal.olimpiadas.Tentativa;

public class TentativaService {
	
	private long proximoId = 1;
    private final List<Tentativa> tentativas;
    private final List<Questao> questoes;

    public TentativaService(List<Tentativa> tentativas, List<Questao> questoes) {
        this.tentativas = tentativas;
        this.questoes = questoes;
    }

    public Tentativa aplicar(Long participanteId, Long provaId) {
        var questoesDaProva = questoes.stream()
                .filter(q -> q.getProvaId() == provaId)
                .toList();

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

}
