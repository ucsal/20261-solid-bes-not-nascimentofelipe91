package br.com.ucsal.olimpiadas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.com.ucsal.service.ParticipanteService;
import br.com.ucsal.service.ProvaService;
import br.com.ucsal.service.TentativaService;

public class App {

	static final List<Participante> participantes = new ArrayList<>();
	static ParticipanteService participanteService = new ParticipanteService(participantes);
	static final List<Prova> provas = new ArrayList<>();
	static ProvaService provaService = new ProvaService(provas);
	static final List<Tentativa> tentativas = new ArrayList<>();
	static TentativaService tentativaService = new TentativaService(tentativas, provaService);

	private static final Scanner in = new Scanner(System.in);

	public static void main(String[] args) {

	    seed();

	    var acoes = criarAcoes();

	    while (true) {

	        exibirMenu();

	        var opcao = in.nextLine();

	        if ("0".equals(opcao)) {
	            System.out.println("fim");
	            return;
	        }

	        var acao = acoes.get(opcao);

	        if (acao != null) {
	            acao.run();
	        } else {
	            System.out.println("opção inválida");
	        }
	    }
	}

	static void cadastrarParticipante() {
		System.out.print("Nome: ");
		var nome = in.nextLine();

		System.out.print("Email: ");
		var email = in.nextLine();

		try {
			var p = participanteService.cadastrar(nome, email);
			System.out.println("Participante cadastrado: " + p.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void cadastrarProva() {
		System.out.print("Título da prova: ");
		var titulo = in.nextLine();

		try {
			var prova = provaService.cadastrar(titulo);
			System.out.println("Prova criada: " + prova.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void cadastrarQuestao() {
		if (provaService.listarTodas().isEmpty()) {
			System.out.println("não há provas cadastradas");
			return;
		}

		var provaId = escolherProva();
		if (provaId == null)
			return;

		System.out.println("Enunciado:");
		var enunciado = in.nextLine();

		var alternativas = new String[5];
		for (int i = 0; i < 5; i++) {
			char letra = (char) ('A' + i);
			System.out.print("Alternativa " + letra + ": ");
			alternativas[i] = letra + ") " + in.nextLine();
		}

		System.out.print("Alternativa correta (A–E): ");
		char correta;

		try {
			correta = Questao.normalizar(in.nextLine().trim().charAt(0));
		} catch (Exception e) {
			System.out.println("alternativa inválida");
			return;
		}

		try {
			provaService.adicionarQuestao(provaId, enunciado, alternativas, correta);
			System.out.println("Questão cadastrada com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	static void aplicarProva() {

		if (participantes.isEmpty()) {
			System.out.println("Cadastre participantes primeiro.");
			return;
		}

		if (provaService.listarTodas().isEmpty()) {
			System.out.println("Cadastre provas primeiro.");
			return;
		}

		var participanteId = escolherParticipante();
		if (participanteId == null)
			return;

		var provaId = escolherProva();
		if (provaId == null)
			return;

		try {
			var prova = provaService.buscarPorId(provaId);

			if (prova == null) {
				System.out.println("Prova não encontrada.");
				return;
			}

			var tentativa = tentativaService.aplicar(participanteId, provaId);

			System.out.println("\n--- Início da Prova ---");
			System.out.println("Prova: " + prova.getTitulo());

			var questoesDaProva = prova.getQuestoes();

			if (questoesDaProva.isEmpty()) {
				System.out.println("Essa prova não possui questões.");
				return;
			}

			int numeroQuestao = 1;

			for (var q : questoesDaProva) {

				System.out.println("\nQuestão #" + numeroQuestao++);

				q.exibir();

				System.out.print("Sua resposta (A–E): ");
				char marcada;

				try {
					marcada = Questao.normalizar(in.nextLine().trim().charAt(0));
				} catch (Exception e) {
					System.out.println("Resposta inválida (marcando como errada).");
					marcada = 'X';
				}

				tentativaService.responder(tentativa, q, marcada);
			}

			System.out.println("\n--- Fim da Prova ---");
			System.out.println("Nota: " + tentativa.calcularNota());

		} catch (Exception e) {
			System.out.println("Erro ao aplicar prova: " + e.getMessage());
		}
	}

	static void listarTentativas() {
		System.out.println("\n--- Tentativas ---");

		var lista = tentativaService.listarTodas();

		for (var t : lista) {
			System.out.printf("#%d | participante=%d | prova=%d | nota=%d/%d%n", t.getId(), t.getParticipanteId(),
					t.getProvaId(), t.calcularNota(), t.getRespostas().size());
		}
	}

	static Long escolherParticipante() {
		System.out.println("\nParticipantes:");

		for (var p : participanteService.listarTodos()) {
			System.out.printf("  %d) %s%n", p.getId(), p.getNome());
		}

		System.out.print("Escolha o id do participante: ");

		try {
			long id = Long.parseLong(in.nextLine());

			if (!participanteService.existePorId(id)) {
				System.out.println("id inválido");
				return null;
			}

			return id;

		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}

	static Long escolherProva() {
		System.out.println("\nProvas:");

		for (var p : provaService.listarTodas()) {
			System.out.printf("  %d) %s%n", p.getId(), p.getTitulo());
		}

		System.out.print("Escolha o id da prova: ");

		try {
			long id = Long.parseLong(in.nextLine());

			if (!provaService.existePorId(id)) {
				System.out.println("id inválido");
				return null;
			}

			return id;

		} catch (Exception e) {
			System.out.println("entrada inválida");
			return null;
		}
	}

	static void seed() {

		var prova = new Prova();
		prova.setId(1);
		prova.setTitulo("Olimpíada 2026 • Nível 1 • Prova A");

		var q1 = new QuestaoXadrez();
		q1.setId(1);

		q1.setEnunciado("""
				Questão 1 — Mate em 1.
				É a vez das brancas.
				Encontre o lance que dá mate imediatamente.
				""");

		q1.setFenInicial("6k1/5ppp/8/8/8/7Q/6PP/6K1 w - - 0 1");

		q1.setAlternativas(new String[] { "A) Qh7#", "B) Qf5#", "C) Qc8#", "D) Qh8#", "E) Qe6#" });

		q1.setAlternativaCorreta('C');

		prova.adicionarQuestao(q1);

		provas.add(prova);
	}
	
	static Map<String, Runnable> criarAcoes() {
	    Map<String, Runnable> acoes = new HashMap<>();

	    acoes.put("1", () -> cadastrarParticipante());
	    acoes.put("2", () -> cadastrarProva());
	    acoes.put("3", () -> cadastrarQuestao());
	    acoes.put("4", () -> aplicarProva());
	    acoes.put("5", () -> listarTentativas());

	    return acoes;
	}
	
	static void exibirMenu() {
	    System.out.println("\n=== OLIMPÍADA DE QUESTÕES (V1) ===");
	    System.out.println("1) Cadastrar participante");
	    System.out.println("2) Cadastrar prova");
	    System.out.println("3) Cadastrar questão (A–E) em uma prova");
	    System.out.println("4) Aplicar prova (selecionar participante + prova)");
	    System.out.println("5) Listar tentativas (resumo)");
	    System.out.println("0) Sair");
	    System.out.print("> ");
	}
}