# Agenda de Contatos

## Descrição

Este projeto é uma agenda de contatos desenvolvida com Spring Boot, utilizando uma abordagem RESTful para a API. A aplicação permite criar, listar, buscar e gerenciar contatos, garantindo a integridade dos dados através de validações e seguindo boas práticas de desenvolvimento.

## Estrutura do Projeto

```
agenda-de-contatos/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/agenda/br/
│   │   │       ├── AgendaApplication.java
│   │   │       ├── config/
│   │   │       │   └── OpenApiConfig.java
│   │   │       ├── controller/
│   │   │       │   └── AgendaController.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── model/
│   │   │       │   ├── Contato.java
│   │   │       │   ├── dto/
│   │   │       │   │   ├── ContatoDTO.java
│   │   │       │   │   ├── ContatoRequest.java
│   │   │   │   │   └── PageResponse.java
│   │   │       ├── repository/
│   │   │       │   └── ContatoRepository.java
│   │   │       ├── service/
│   │   │           └── ContatoService.java
│   ├── test/
│       └── java/
│           └── com/agenda/br/
│               └── AgendaApplicationTests.java
```

## Linguagens de Programação

- Java 17 ou superior

## Dependências

- Spring Boot
- Spring Data JPA
- Spring Web
- Spring Validation
- Swagger/OpenAPI
- Spring Security (para autenticação, se aplicável)

### Instruções de Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/agenda-de-contatos.git
   ```
2. Acesse o diretório do projeto:
   ```bash
   cd agenda-de-contatos
   ```
3. Instale as dependências usando Maven ou Gradle (exemplo com Maven):
   ```bash
   mvn clean install
   ```
4. Configure o arquivo `application.properties` ou `application.yml` com as informações do banco de dados.

### Como Rodar o Projeto

1. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
2. Acesse a API em `http://localhost:8080/api/contatos`.

### Executando Testes

1. Execute os testes com Maven:
   ```bash
   mvn test
   ```

## Explicação Detalhada dos Arquivos de Código

### `ContatoDTO.java`

**Localização:** `src/main/java/com/agenda/br/model/dto/ContatoDTO.java`

**Responsabilidades:**
- Representar os dados de um contato.
- Facilitar a transferência de dados entre a camada de serviço e a camada de apresentação.

**Atributos:**
- `id`: Identificador único do contato.
- `nome`: Nome do contato.
- `telefone`: Telefone do contato.
- `email`: Email do contato.
- `endereco`: Endereço do contato.
- `observacoes`: Observações adicionais.
- `dataCadastro`: Data de cadastro do contato.
- `dataAtualizacao`: Data da última atualização do contato.

### `OpenApiConfig.java`

**Localização:** `src/main/java/com/agenda/br/config/OpenApiConfig.java`

**Responsabilidades:**
- Configurar a documentação da API usando OpenAPI/Swagger.
- Definir metadados da API, como título, versão, descrição, contato e licença.

**Atributos e Métodos:**
- Configuração da API com `@OpenAPIDefinition` e `@Info`.
- Define o esquema de segurança (`bearerAuth`) e requisitos de segurança para a API.

### `AgendaController.java`

**Localização:** `src/main/java/com/agenda/br/controller/AgendaController.java`

**Responsabilidades:**
- Gerenciar requisições HTTP relacionadas aos contatos.
- Utilizar `ContatoService` para realizar operações de CRUD.

**Endpoints:**
- `@PostMapping`: Cria um novo contato.
- `@GetMapping`: Lista contatos paginados.
- `@RequestParam`: Parâmetros de paginação e busca.

### `ContatoRepository.java`

**Localização:** `src/main/java/com/agenda/br/repository/ContatoRepository.java`

**Responsabilidades:**
- Definir operações de banco de dados para a entidade `Contato`.
- Utilizar `JpaRepository` para operações CRUD.

**Métodos:**
- `findAll(Pageable pageable)`: Retorna uma página de contatos.
- `buscarPorTermo(String termo, Pageable pageable)`: Busca contatos por termo com suporte a paginação.
- `existsByEmail(String email)`: Verifica a existência de um contato pelo email.
- `existsByEmailAndIdNot(String email, Long id)`: Verifica a unicidade do email durante atualizações.

### `ContatoRequest.java`

**Localização:** `src/main/java/com/agenda/br/model/dto/ContatoRequest.java`

**Responsabilidades:**
- Encapsular dados de contato enviados pelo cliente.
- Validar os dados de entrada (`@NotBlank`, `@Pattern`, `@Email`).

### `AgendaApplication.java`

**Localização:** `src/main/java/com/agenda/br/AgendaApplication.java`

**Responsabilidades:**
- Ponto de entrada da aplicação Spring Boot.
- Inicializar a aplicação e configurar automaticamente o ambiente.

### `PageResponse.java`

**Localização:** `src/main/java/com/agenda/br/model/dto/PageResponse.java`

**Responsabilidades:**
- Encapsular resultados paginados, incluindo metadados como número total de páginas, tamanho da página, e se é a última página.

### `GlobalExceptionHandler.java`

**Localização:** `src/main/java/com/agenda/br/exception/GlobalExceptionHandler.java`

**Responsabilidades:**
- Tratar exceções globalmente e fornecer respostas padronizadas.
- Métodos para tratar `ApiRequestException`, `ResourceNotFoundException` e outras exceções.

### `ContatoService.java`

**Localização:** `src/main/java/com/agenda/br/service/ContatoService.java`

**Responsabilidades:**
- Lógica de negócios para gerenciar contatos.
- Conversão entre entidades e DTOs.
- Interação com o `ContatoRepository` para operações de banco de dados.

## Exemplos de Uso

### Criar um Novo Contato

```bash
curl -X POST "http://localhost:8080/api/contatos" -H "Content-Type: application/json" -d '{
    "nome": "João Silva",
    "telefone": "(11) 98765-4321",
    "email": "joao.silva@example.com",
    "endereco": "Rua das Flores, 123",
    "observacoes": "Amigo de infância"
}'
```

### Listar Contatos Paginados

```bash
curl -X GET "http://localhost:8080/api/contatos?page=0&size=10&sort=nome,asc"
```

### Buscar Contatos por Termo

```bash
curl -X GET "http://localhost:8080/api/contatos/buscar?termo=Joao&page=0&size=10"
```

## Boas Práticas e Dicas para Contribuir

1. **Validação de Dados:** Use anotações de validação (`@NotBlank`, `@Email`, etc.) para garantir a integridade dos dados.
2. **Paginação e Ordenação:** Utilize `Pageable` e `Sort` do Spring Data para suportar paginação e ordenação em endpoints de listagem.
3. **Documentação da API:** Use anotações Swagger para documentar endpoints, parâmetros e respostas.
4. **Tratamento de Exceções:** Centralize o tratamento de exceções usando `@ControllerAdvice` e `@ExceptionHandler`.
5. **Separar Responsabilidades:** Mantenha a separação de responsabilidades entre controladores, serviços e repositórios.
6. **Testes:** Escreva testes para suas funcionalidades usando JUnit e Mockito.
7. **DTOs:** Utilize DTOs para transferir dados entre camadas e APIs, mantendo a separação entre a entidade de domínio e a representação de transferência.

## Contribuindo

Para contribuir com este projeto, siga estas diretrizes:

1. Fork o repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`).
3. Faça commit de suas mudanças (`git commit -am 'Adiciona nova funcionalidade'`).
4. Push para a branch (`git push origin feature/nova-funcionalidade`).
5. Abra um Pull Request.

Verifique a documentação em [https://docs.maritaca.ai](https://docs.maritaca.ai) para mais detalhes sobre como utilizar e contribuir com projetos utilizando a Maritaca AI.