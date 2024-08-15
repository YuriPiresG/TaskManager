# Gerenciador de Tarefas Waterfy

Este é um projeto de demonstração utilizando Spring Boot. O projeto inclui uma API para gerenciar tarefas.

## Configuração

### Pré-requisitos

- Java 22
- Maven
- Docker (opcional, para executar o banco de dados PostgreSQL)

### Passos para configurar o projeto

1. Clone o repositório:
    ```sh
    git clone <URL_DO_REPOSITORIO>
    cd projeto
    ```

2. Configure o banco de dados PostgreSQL utilizando Docker:
    ```sh
    docker-compose up -d
    ```

3. Instale as dependências do projeto:
    ```sh
    mvn clean install
    ```

## Executando o Projeto

1. Execute a aplicação:
    ```sh
    mvn spring-boot:run
    ```

2. A aplicação estará disponível em `http://localhost:8080`.

## Estrutura do Projeto

- `src/main/java/com/waterfy/projeto`: Contém o código fonte principal da aplicação.
  - `ProjetoApplication.java`: Classe principal que inicia a aplicação Spring Boot.
  - `tasks/`: Pacote que contém as classes relacionadas às tarefas.
    - `Task.java`: Entidade que representa uma tarefa.
    - `TasksController.java`: Controlador REST para gerenciar as tarefas.
    - `TasksRepository.java`: Repositório JPA para acessar os dados das tarefas.
    - `TasksServices.java`: Serviço que contém a lógica de negócio para as tarefas.
- `src/main/resources`: Contém os recursos da aplicação.
  - `application.properties`: Arquivo de configuração da aplicação.
- `src/test/java/com/waterfy/projeto`: Contém os testes da aplicação.
  - `ProjetoApplicationTests.java`: Classe de teste para verificar se o contexto da aplicação carrega corretamente.

## Decisões Técnicas

- **Spring Boot**: Utilizado para simplificar a configuração e o desenvolvimento da aplicação.
- **Spring Data JPA**: Utilizado para facilitar o acesso aos dados no banco de dados PostgreSQL.
- **Lombok**: Utilizado para reduzir o código boilerplate, como getters, setters e construtores.
- **Docker Compose**: Utilizado para configurar e executar o banco de dados PostgreSQL de forma simples.
- **Validação**: Utilizado `jakarta.validation` para validar os campos das entidades.

## Endpoints da API

- `GET /tasks`: Retorna todas as tarefas.
- `POST /tasks`: Cria uma nova tarefa.
- `GET /tasks/{id}`: Retorna uma tarefa específica pelo ID.
- `DELETE /tasks/{id}`: Deleta uma tarefa específica pelo ID.
- `GET /tasks/uncompleted`: Retorna todas as tarefas não concluídas.
- `DELETE /tasks/completed`: Deleta todas as tarefas concluídas.

## Contribuição

1. Faça um fork do projeto.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`).
4. Faça um push para a branch (`git push origin feature/nova-feature`).
5. Crie um novo Pull Request.

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## Task
- [x] Create a task
- [x] Get a task by id
- [x] Get all tasks
- [x] Get all uncompleted tasks
- [x] Update a task
- [x] Delete a task
- [x] Delete all tasks
- [x] Delete completed tasks