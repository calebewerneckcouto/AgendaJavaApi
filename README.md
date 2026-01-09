# Agenda API

Bem-vindo ao repositório da Agenda API, um sistema RESTful para gerenciamento de contatos. Este documento fornece uma visão geral do projeto, incluindo sua estrutura, dependências, instruções de instalação, execução e contribuição.

## Estrutura do Projeto

```
agenda-api/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── agenda/
│   │   │           ├── br/
│   │   │           │   ├── config/
│   │   │           │   │   └── OpenApiConfig.java
│   │   │           │   ├── controller/
│   │   │           │   │   └── AgendaController.java
│   │   │           │   ├── exception/
│   │   │           │   │   └── GlobalExceptionHandler.java
│   │   │           │   ├── model/
│   │   │           │   │   ├── Contato.java
│   │   │           │   │   └── dto/
│   │   │           │   │       ├── ContatoDTO.java
│   │   │           │   │       ├── ContatoRequest.java
│   │   │           │   │       └── PageResponse.java
│   │   │           │   ├── repository/
│   │   │           │   │   └── ContatoRepository.java
│   │   │           │   ├── service/
│   │   │           │   │   └── ContatoService.java
│   │   │           │   └── AgendaApplication.java
│   └── test/
│       └── java/
│           └── com/
│               └── agenda/
│                   └── br/
│                       └── AgendaApplicationTests.java
└── README.md
```

## Linguagens de Programação Usadas

- Java

## Dependências e Instruções de Instalação

### Dependências

- Spring Boot
- Spring Data JPA
- Spring Web
- Hibernate Validator
- Swagger/OpenAPI
- JUnit 5

### Instalação

1. **Clone o repositório:**

   ```bash
   git clone https://github.com/seu-usuario/agenda-api.git
   cd agenda-api
   ```

2. **Configure o banco de dados:**

   Certifique-se de que um banco de dados PostgreSQL esteja em execução e configure as propriedades de conexão no arquivo `application.properties`.

3. **Compile e construa o projeto:**

   ```bash
   ./mvnw clean install
   ```

## Como Rodar o Projeto e Executar Testes

### Executar o Projeto

Para iniciar a aplicação, execute o seguinte comando:

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

### Executar Testes

Para executar os testes, use o comando:

```bash
./mvnw test
```

## Explicação Detalhada dos Arquivos de Código

### `ContatoDTO.java`

- **Atributos:** `id`, `nome`, `telefone`, `email`, `endereco`, `observacoes`, `dataCadastro`, `dataAtualizacao`.
- **Responsabilidade:** Transferir dados de contato entre camadas da aplicação.

### `OpenApiConfig.java`

- **Responsabilidade:** Configurar a documentação da API usando OpenAPI/Swagger.

### `AgendaController.java`

- **Responsabilidade:** Gerenciar endpoints RESTful para operações de CRUD de contatos.

### `GlobalExceptionHandler.java`

- **Responsabilidade:** Tratar exceções globais e fornecer respostas HTTP consistentes.

### `Contato.java`

- **Responsabilidade:** Representar a entidade de contato no banco de dados.

### `ContatoService.java`

- **Responsabilidade:** Implementar a lógica de negócios para operações de contato.

### `ContatoRepository.java`

- **Responsabilidade:** Acessar dados de contato no banco de dados usando Spring Data JPA.

### `AgendaApplication.java`

- **Responsabilidade:** Classe principal para iniciar a aplicação Spring Boot.

### `PageResponse.java`

- **Responsabilidade:** Encapsular dados de resposta paginada.

## Exemplos de Uso

### Criar um Contato

```http
POST /api/contatos
Content-Type: application/json

{
  "nome": "João Silva",
  "telefone": "(11) 98765-4321",
  "email": "joao.silva@example.com",
  "endereco": "Rua Exemplo, 123",
  "observacoes": "Cliente importante"
}
```

### Listar Contatos

```http
GET /api/contatos?page=0&size=10
```

## Boas Práticas e Dicas para Contribuir

1. **Siga o padrão de codificação:** Mantenha o código limpo e organizado.
2. **Escreva testes:** Adicione testes para novas funcionalidades e correções de bugs.
3. **Documente suas alterações:** Atualize o README e a documentação da API conforme necessário.
4. **Faça pull requests pequenos:** Divida suas contribuições em partes menores e mais gerenciáveis.
5. **Revise o código:** Antes de enviar um pull request, revise seu código e peça feedback de outros desenvolvedores.

Agradecemos por contribuir para a Agenda API! Se tiver dúvidas ou sugestões, sinta-se à vontade para abrir uma issue ou entrar em contato.