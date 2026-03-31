package br.com.ucsal.service;

import java.util.ArrayList;
import java.util.List;

import br.com.ucsal.olimpiadas.Participante;

public class ParticipanteService {
	
	private List<Participante> participantes = new ArrayList<>();
	private static long proximoId  = 1;
	
	public ParticipanteService(List<Participante> participantes) {
        this.participantes = participantes;
    }
	
	public Participante cadastrar(String nome, String email) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        var p = new Participante();
        p.setId(proximoId++);
        p.setNome(nome);
        p.setEmail(email);

        participantes.add(p);
        return p;
    }
}
