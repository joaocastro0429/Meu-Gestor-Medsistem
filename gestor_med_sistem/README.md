# Gestor Med Sistem API

API REST para administrar empresas, filiais, usuários, planos SaaS, módulos, assinaturas e permissões.

O projeto foi organizado de forma didática no fluxo:

```text
Controller -> Service -> Repository -> PostgreSQL
```

## Tecnologias utilizadas

| Tecnologia | Uso no projeto |
|---|---|
| Java 17 | Linguagem principal |
| Spring Boot 4.1 | Inicialização e configuração da aplicação |
| Spring Web | Controllers e endpoints REST |
| Spring Data JPA | Persistência e repositories |
| Hibernate | Mapeamento das entidades para tabelas |
| Bean Validation | Validação dos Request Bodies |
| PostgreSQL 17 | Banco de dados da aplicação |
| Flyway | Criação e versionamento do banco |
| Docker Compose | Execução local do PostgreSQL |
| H2 | Banco em memória usado nos testes |
| Spring Security + JWT | Login, senha BCrypt e proteção das rotas com Bearer Token |
| Lombok | Redução de código repetitivo nas entidades |
| Maven Wrapper | Compilação sem instalar Maven globalmente |

## Pré-requisitos

Instale antes de executar:

- [Git](https://git-scm.com/downloads)
- [Java JDK 17](https://adoptium.net/temurin/releases/?version=17)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)
- Um editor como [Visual Studio Code](https://code.visualstudio.com/) ou IntelliJ IDEA

Confira as instalações:

```bash
git --version
java -version
docker --version
docker compose version
```

O resultado do Java deve indicar a versão 17.

## Como baixar o projeto

### Opção 1: usando Git

Na página do repositório, copie o endereço disponibilizado no botão **Code**. Depois execute:

```bash
git clone URL_DO_REPOSITORIO
cd gestor_med_sistem
```

Exemplo de URL:

```text
https://github.com/SEU_USUARIO/gestor_med_sistem.git
```

Substitua `URL_DO_REPOSITORIO` pela URL real do projeto.

### Opção 2: baixando ZIP

1. Abra o repositório no GitHub.
2. Clique em **Code**.
3. Clique em **Download ZIP**.
4. Extraia o arquivo.
5. Abra a pasta extraída no editor.

## Como executar

### 1. Iniciar o PostgreSQL

Na raiz do projeto, onde está o arquivo `compose.yaml`, execute:

```bash
docker compose up -d
```

Confira se o container está funcionando:

```bash
docker compose ps
```

O Docker cria automaticamente:

```text
Banco: gestor_med
Usuário: gestor_med
Senha: gestor_med
Porta: 5432
```

Os dados ficam armazenados no volume `postgres_data` e permanecem salvos quando o container é reiniciado.

### 2. Executar a aplicação

No Linux, macOS, Git Bash ou WSL:

```bash
./mvnw spring-boot:run
```

No Prompt de Comando ou PowerShell do Windows:

```powershell
mvnw.cmd spring-boot:run
```

Quando aparecer uma mensagem parecida com esta, a aplicação está pronta:

```text
Started GestorMedSistemApplication
```

A API estará disponível em:

```text
http://localhost:8080/api
```

Na primeira execução, o Flyway aplica a migration:

```text
src/main/resources/db/migration/V1__create_initial_schema.sql
```

## Testar pelo REST Client

No VS Code, instale a extensão **REST Client**. Primeiro execute, na ordem, as quatro requisições de [`auth.http`](auth.http). Elas criam os dados iniciais, fazem login e testam o token.

Depois copie o valor de `accessToken` para a variável `token` no início de [`api.http`](api.http), localize a requisição desejada e clique em **Send Request**.

Execute as criações na ordem do arquivo, pois alguns recursos dependem de outros. Por exemplo, uma assinatura precisa de uma empresa e de um plano existentes.

## Autenticação JWT

O cadastro inicial de empresa, o cadastro inicial de usuário e o login são públicos. As demais rotas exigem token.

O usuário deve ser criado com uma senha de 8 a 72 caracteres. A senha nunca é devolvida pela API e é armazenada como hash BCrypt.

Login:

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "admin@gestormed.com.br",
  "password": "Admin@123"
}
```

Resposta:

```json
{
  "accessToken": "eyJ...",
  "tokenType": "Bearer",
  "expiresIn": 7200,
  "expiresAt": "2026-08-18T20:00:00Z"
}
```

Envie o token nas rotas protegidas:

```http
GET http://localhost:8080/api/modules
Authorization: Bearer eyJ...
```

O token contém o ID, e-mail, empresa e tipo do usuário, e expira em duas horas por padrão.

Exemplo:

```http
POST http://localhost:8080/api/plans
Content-Type: application/json

{
  "name": "Plano Profissional",
  "defaultPrice": 199.90,
  "durationDays": 30,
  "status": "ACTIVE"
}
```

## Executar os testes

Os testes usam H2 em memória, portanto não precisam do Docker:

```bash
./mvnw clean test
```

No Windows:

```powershell
mvnw.cmd clean test
```

## Estrutura do projeto

```text
src
|-- main
|   |-- java/br/com/meugestormedsistem
|   |   |-- config       Configurações do Spring
|   |   |-- domain       Entidades, IDs compostos e enums
|   |   |-- repository   Acesso ao banco com Spring Data JPA
|   |   |-- service      Regras de negócio
|   |   `-- web           Controllers e tratamento de erros
|   `-- resources
|       |-- db/migration  Scripts versionados do Flyway
|       `-- application.properties
`-- test                  Testes automatizados
```

## Recursos da API

| Recurso | Rota base |
|---|---|
| Planos | `/api/plans` |
| Módulos | `/api/modules` |
| Empresas | `/api/companies` |
| Assinaturas | `/api/subscriptions` |
| Filiais | `/api/branches` |
| Configurações | `/api/company-settings` |
| Usuários | `/api/users` |
| Tokens de redefinição | `/api/password-reset-tokens` |
| Módulos do plano | `/api/plan-modules` |
| Módulos da empresa | `/api/company-modules` |
| Permissões | `/api/user-permissions` |
| Filiais do usuário | `/api/user-branches` |

Os recursos principais utilizam:

| Verbo | Exemplo | Função |
|---|---|---|
| GET | `/api/plans` | Listar |
| GET | `/api/plans/{id}` | Buscar por UUID |
| POST | `/api/plans` | Criar |
| PUT | `/api/plans/{id}` | Atualizar os dados completos |
| PATCH | `/api/plans/{id}/status` | Atualizar um campo específico |
| DELETE | `/api/plans/{id}` | Excluir |

## Respostas de erro

Os erros usam o `ProblemDetail`, padrão nativo do Spring:

```json
{
  "type": "about:blank",
  "title": "Bad Request",
  "status": 400,
  "detail": "Existem campos inválidos",
  "instance": "/api/modules",
  "fields": {
    "code": "não deve estar em branco"
  }
}
```

Principais status HTTP:

| Status | Significado |
|---|---|
| 200 | Operação realizada |
| 201 | Recurso criado |
| 204 | Recurso excluído, sem corpo de resposta |
| 400 | JSON, parâmetro ou campo inválido |
| 404 | Recurso não encontrado |
| 409 | Dado duplicado ou conflito de integridade |
| 500 | Erro interno inesperado |

## Configuração do banco

Os valores padrão estão em `application.properties`. Para usar outro PostgreSQL, configure:

```text
DB_URL=jdbc:postgresql://localhost:5432/gestor_med
DB_USERNAME=gestor_med
DB_PASSWORD=gestor_med
JWT_SECRET=uma-chave-secreta-com-pelo-menos-32-caracteres
JWT_EXPIRATION_SECONDS=7200
```

Exemplo no Linux, macOS ou WSL:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/gestor_med
export DB_USERNAME=gestor_med
export DB_PASSWORD=gestor_med
export JWT_SECRET=uma-chave-secreta-com-pelo-menos-32-caracteres
./mvnw spring-boot:run
```

## Parar o ambiente

Para parar o PostgreSQL sem apagar os dados:

```bash
docker compose down
```

Para iniciar novamente:

```bash
docker compose up -d
```

## Problemas comuns

### A porta 5432 já está em uso

Pare outro PostgreSQL ou altere a porta publicada no `compose.yaml`.

### A porta 8080 já está em uso

Encerre a aplicação que está usando essa porta ou inicie com outra:

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### Recebi `409 Conflict`

O dado provavelmente já existe. Liste os registros antes de repetir um POST. O volume Docker preserva os dados entre execuções.

### O VS Code mostra erros antigos

Execute **Java: Clean Java Language Server Workspace** pela paleta de comandos (`Ctrl + Shift + P`).
