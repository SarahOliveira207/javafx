# 📚 Guia Completo de Apresentação e Defesa do Código
## Atividade Avaliativa – 3º Bimestre (LP VI - Java / SOLID / MVC / FXML)

Este guia contém as respostas didáticas e fundamentadas para todas as perguntas que a professora pode fazer durante a avaliação oral e prática do seu projeto.

---

## 🎯 1. Função de Cada Classe e Interface Criada

| Componente | Tipo | Pacote | Responsabilidade (O que faz?) |
|---|---|---|---|
| [`Validador<T>`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/Validador.java) | `Interface` | `com.template.validator` | Define o **contrato genérico de validação** com os métodos `validar(T valor)`, `getMensagemErro()` e `getValor()`. Permite validar qualquer tipo de dado (String, Integer, etc.) aplicando OCP e LSP. |
| [`CamposObrigatoriosValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/CamposObrigatoriosValidador.java) | `Classe` | `com.template.validator` | Implementa `Validador<String>` para verificar se campos de texto obrigatórios (como Nome/Modelo e Cor) não são nulos nem vazios. |
| [`NumeroValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/NumeroValidador.java) | `Classe` | `com.template.validator` | Implementa `Validador<String>` para verificar se campos numéricos (como Potência em Cavalos) contêm números inteiros válidos e positivos. |
| [`EmailValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/EmailValidador.java) | `Classe` | `com.template.validator` | Implementa `Validador<String>` para verificar o formato de endereços de e-mail via Regex. |
| [`ICarroValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/ICarroValidador.java) | `Interface` | `com.template.validator` | Interface de alto nível que define o método `boolean validarCarro(String nome, String cavalos, String cor)`. É a **abstração** da qual o `MainController` depende (DIP e ISP). |
| [`CarroValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/CarroValidador.java) | `Classe` | `com.template.validator` | Implementa `ICarroValidador`. Cria uma lista genérica `List<Validador<String>>`, adiciona os validadores dos campos e itera sobre eles com `foreach`. Se algum falhar, exibe alerta amigável e retorna `false`. |
| [`MainController`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/controller/MainController.java) | `Classe` | `com.template.controller` | Controla os eventos da tela FXML (cliques nos botões Inserir, Salvar, Excluir). **Não instancia validadores diretamente**; recebe `ICarroValidador` via construtor (Injeção de Dependência). |
| [`Main`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/main/Main.java) | `Classe` | `com.template.main` | Ponto de entrada do JavaFX. Cria a dependência concreta `new CarroValidador()` e a injeta no `MainController` através do `loader.setControllerFactory(...)`. |
| [`CarroService`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/service/CarroService.java) | `Classe` | `com.template.service` | Concentra as regras de negócio, conversão de dados e orquestração com o DAO. |
| [`CarroDAO`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/model/CarroDAO.java) | `Classe` | `com.template.model` | Responsável exclusivamente pelos comandos SQL e persistência no banco de dados via JDBC. |
| [`CarroDTO`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/model/dto/CarroDTO.java) | `Classe` | `com.template.model.dto` | Objeto de transferência de dados que representa a entidade Veículo. |
| [`DialogUtil`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/util/DialogUtil.java) | `Classe` | `com.template.util` | Utilitário para exibir alertas visuais (`showWarning`, `showInfo`, `showError`, `showConfirmation`). |

---

## 🎤 2. Respostas para as Perguntas da Avaliação Oral

### ❓ P1: Por que a interface `Validador<T>` utiliza Generics (`<T>`)?
> **Resposta:** 
> "Utilizamos Generics para tornar a interface reutilizável para qualquer tipo de dado. Hoje validamos `String`, mas no futuro poderíamos validar diretamente um `Integer`, uma data `LocalDate` ou um objeto complexo `CarroDTO`, sem precisar criar uma nova interface para cada tipo."

### ❓ P2: Qual o funcionamento dos três métodos da interface `Validador<T>`?
> **Resposta:**
> 1. `boolean validar(T valor)`: Executa a lógica da regra e retorna `true` se o valor estiver correto ou `false` se estiver inválido.
> 2. `String getMensagemErro()`: Retorna a mensagem amigável que explica exatamente por que a validação falhou (ex: *'O campo Nome/Modelo é obrigatório'*).
> 3. `T getValor()`: Retorna o valor original armazenado no validador para que ele possa ser validado de forma autônoma.

### ❓ P3: Como os validadores são armazenados e percorridos em `CarroValidador`?
> **Resposta:**
> "Eles são armazenados em uma lista genérica do tipo `List<Validador<String>> validadores = new ArrayList<>();`.
> Em seguida, utilizamos um laço `foreach` para percorrer a lista:
> ```java
> for (Validador<String> validador : validadores) {
>     if (!validador.validar(validador.getValor())) {
>         DialogUtil.showWarning(validador.getMensagemErro());
>         return false;
>     }
> }
> ```
> O `CarroValidador` não precisa saber os detalhes internos de como cada campo é validado; ele apenas executa o contrato definido pela interface `Validador`."

### ❓ P4: Por que o `MainController` utiliza a interface `ICarroValidador` em vez da classe concreta `CarroValidador`?
> **Resposta:**
> "Para aplicar o **Princípio da Inversão de Dependência (DIP)** e reduzir o acoplamento. O Controller depende de uma abstração (`ICarroValidador`). Se amanhã precisarmos trocar o validador por um validador de testes (*Mock*), ou por uma validação assíncrona, não precisamos alterar nenhuma linha do Controller."

### ❓ P5: Onde o `CarroValidador` é instanciado e como ocorre a Injeção de Dependência?
> **Resposta:**
> "Ele é instanciado na classe principal [`Main.java`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/main/Main.java) (fora do Controller).
> A injeção ocorre utilizando a **Fábrica de Controladores do JavaFX** (`loader.setControllerFactory`). Quando o JavaFX vai criar o `MainController`, a fábrica intercepta a criação e passa o validador pronto no construtor:
> ```java
> ICarroValidador carroValidador = new CarroValidador();
> loader.setControllerFactory(controllerClass -> {
>     if (controllerClass == MainController.class) {
>         return new MainController(carroValidador);
>     }
>     ...
> });
> ```"

### ❓ P6: Onde a validação é chamada no Controller?
> **Resposta:**
> "Tanto no método **`btnInserir` (Cadastro)** quanto no **`btnSalvar` (Atualização)**. Antes de qualquer operação de banco ou serviço, existe a checagem:
> ```java
> if (!carroValidador.validarCarro(fieldNome.getText(), fieldCavalos.getText(), fieldCor.getText())) {
>     return; // Para imediatamente se algum campo estiver inválido
> }
> ```"

---

## 💎 3. Os Princípios SOLID Aplicados no Projeto

| Princípio | Onde foi aplicado? | Justificativa |
|---|---|---|
| **S - Single Responsibility Principle** (Responsabilidade Única) | Em todas as camadas (`MainController`, `CarroService`, `CarroDAO`, Validadores). | Cada classe possui apenas um motivo para mudar: o Controller só cuida de UI, o DAO só cuida de SQL, o Service cuida das regras e os Validadores cuidam das regras de entrada. |
| **O - Open/Closed Principle** (Aberto/Fechado) | Na interface [`Validador<T>`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/Validador.java). | Podemos adicionar novos validadores (ex: `AnoValidador`, `PlacaValidador`) criando novas classes sem precisar alterar o código dos validadores já existentes. |
| **L - Liskov Substitution Principle** (Substituição de Liskov) | Em [`CamposObrigatoriosValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/CamposObrigatoriosValidador.java) e [`NumeroValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/NumeroValidador.java). | Qualquer classe que implementa `Validador<String>` pode ser colocada na lista de `CarroValidador` e tratada pelo `foreach` sem quebrar o comportamento do sistema. |
| **I - Interface Segregation Principle** (Segregação de Interfaces) | Em [`ICarroValidador`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/ICarroValidador.java) e [`Validador<T>`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/validator/Validador.java). | Interfaces enxutas e coesas com métodos estritamente necessários, sem forçar classes a implementar métodos que não usam. |
| **D - Dependency Inversion Principle** (Inversão de Dependência) | No [`MainController`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/controller/MainController.java) e [`Main.java`](file:///c:/Users/ra2457087/IdeaProjects/MVCCarro/src/java/com/template/main/Main.java). | O Controller depende da abstração `ICarroValidador`, e a instância concreta é injetada via construtor pela `ControllerFactory` no `Main`. |
