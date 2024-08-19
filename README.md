# Gerenciador de Tarefas Waterfy

Este é um projeto de demonstração utilizando Spring Boot. O projeto inclui uma API para gerenciar tarefas.

## Configuração

### Pré-requisitos

- [Java 21](https://www.oracle.com/br/java/technologies/downloads/#jdk21-windows)
- [Maven 3.9.4](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop/)

### Passos para configurar o projeto

1. Clone o repositório:

   ```sh
   git clone https://github.com/YuriPiresG/waterfyProject.git
   ```

2. Build do projeto:
   Certifique-se de que o Docker esteja em execução e execute o seguinte comando, dentro da pasta do projeto, para criar a imagem do banco de dados PostgreSQL:
   ```sh
   docker-compose up --build
   ```

## Executando o Projeto

1. Execute a aplicação:

   ```sh
   mvn spring-boot:run
   ```

2. A aplicação estará disponível em `http://localhost:8080/tasks`.

3. O Swagger estará disponível em `http://localhost:8080/swagger-ui/index.html#/`.

## Estrutura do Projeto

- `sql/`: Contém o DDL para criar o banco de dados e a tabela de tarefas. Não é utilizado, pois o banco de dados é criado automaticamente pelo Spring Boot.
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

  ## Visual

  ```bash
  📦src
  ┣ 📂main
  ┃ ┣ 📂java
  ┃ ┃ ┗ 📂com
  ┃ ┃ ┃ ┗ 📂waterfy
  ┃ ┃ ┃ ┃ ┗ 📂projeto
  ┃ ┃ ┃ ┃ ┃ ┣ 📂enums
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂validation
  ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ValueOfEnum.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ValueOfEnumValidator.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TaskStatus.java
  ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ApiException.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ApiExceptionHandler.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ApiRequestException.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TaskNotFoundException.java
  ┃ ┃ ┃ ┃ ┃ ┣ 📂tasks
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
  ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CreateTaskDTO.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TaskDTO.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UpdateTaskDTO.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Task.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TasksController.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TasksRepository.java
  ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TasksServices.java
  ┃ ┃ ┃ ┃ ┃ ┗ 📜ProjetoApplication.java
  ┃ ┗ 📂resources
  ┃ ┃ ┣ 📂static
  ┃ ┃ ┣ 📂templates
  ┃ ┃ ┣ 📜application-test.yml
  ┃ ┃ ┗ 📜application.yml
  ┗ 📂test
  ┃ ┗ 📂java
  ┃ ┃ ┗ 📂com
  ┃ ┃ ┃ ┗ 📂waterfy
  ┃ ┃ ┃ ┃ ┗ 📂projeto
  ┃ ┃ ┃ ┃ ┃ ┣ 📜TasksControllerTest.java
  ┃ ┃ ┃ ┃ ┃ ┗ 📜TasksServicesTest.java
  ```

## Decisões Técnicas

- **Spring Boot**: Utilizado para simplificar a configuração e o desenvolvimento da aplicação.
- **Spring Data JPA**: Utilizado para facilitar o acesso aos dados no banco de dados PostgreSQL.
- **H2 Database**: Utilizado para o banco de dados em memória para testes.
- **Swagger**: Utilizado para documentar a API.
- **Java 21**: Utilizado por ser a LTS mais recente.
- **Lombok**: Utilizado para reduzir o código boilerplate, como getters, setters e construtores.
- **Docker Compose**: Utilizado para configurar e executar o banco de dados PostgreSQL de forma simples.
- **Validação**: Utilizado `jakarta.validation` para validar os campos das entidades.

### DER do Banco de Dados

![DER](DER.png)