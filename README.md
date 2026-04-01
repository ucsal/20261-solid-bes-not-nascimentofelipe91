Alterações:

Criação de classes de serviço (ParticipanteService, ProvaService, TentativaService e AvaliaçãoService) para centralizar regras de negócio.
Refatoração do menu principal utilizando Map<String, Runnable>, substituindo switch-case.
Separação da lógica do menu em dois métodos auxiliares (criarAcoes e exibirMenu) para melhorar organização.
Utilização de agregação entre a classe Prova e Questões.
Mudança dos contadores para serem gerenciados pelos serviços.
Refatoração da classe Questao, removendo comportamentos específicos e deixando-a mais genérica
Criação da classe QuestaoXadrez para tratar regras específicas ao xadrez.
Aplicação de herança para especializar tipos de questão sem alterar a base.


Uso do SOLID:

SRP — Single Responsibility Principle
  App ficou responsável apenas pela interação com o usuário.
  Services concentram regras de negócio.
  Questao e QuestaoXadrez cuidam apenas dos dados e comportamento da questão.

OCP — Open/Closed Principle
  O menu pode ser estendido adicionando novas entradas no Map<String, Runnable> sem alterar o main.
  O sistema permite adicionar novos tipos de questão (ex: QuestaoXadrez) sem modificar código existente.

LSP — Liskov Substitution Principle
  QuestaoXadrez pode ser usada no lugar de Questao sem quebrar o sistema.
  A lista de questões (List<Questao>) funciona com qualquer subclasse de Questao.

ISP — Interface Segregation Principle
  As classes não dependem de métodos que não utilizam.

DIP — Dependency Inversion Principle
  O App depende de serviços (ParticipanteService, ProvaService, TentativaService) ao invés de manipular diretamente os dados.
