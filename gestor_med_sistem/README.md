# Gestor Med Sistem API

API REST em Spring Boot, PostgreSQL, JPA e Flyway.

## Executar

Requisitos: Java 17 e Docker.

```bash
docker compose up -d
./mvnw spring-boot:run
```

A API fica disponível em `http://localhost:8080/api`. O schema é criado automaticamente pelo Flyway. Os dados do PostgreSQL ficam no volume `postgres_data`.

Para usar outro banco, defina `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`.

## Rotas

Cada recurso de UUID simples oferece:

| Verbo | Rota | Operação |
|---|---|---|
| GET | `/api/{recurso}` | Listar todos |
| GET | `/api/{recurso}/{id}` | Buscar por ID |
| POST | `/api/{recurso}` | Criar |
| PUT | `/api/{recurso}/{id}` | Substituir |
| PATCH | `/api/{recurso}/{id}` | Atualizar campos informados |
| DELETE | `/api/{recurso}/{id}` | Excluir |

Recursos: `plans`, `modules`, `companies`, `subscriptions`, `branches`, `company-settings`, `users` e `password-reset-tokens`.

Os relacionamentos com chave composta usam os dois UUIDs na rota:

| Recurso | Rota de um item |
|---|---|
| Módulos do plano | `/api/plan-modules/{planId}/{moduleId}` |
| Módulos da empresa | `/api/company-modules/{companyId}/{moduleId}` |
| Permissões do usuário | `/api/user-permissions/{userId}/{moduleId}` |
| Filiais do usuário | `/api/user-branches/{userId}/{branchId}` |

Esses recursos também aceitam GET, POST, PUT, PATCH e DELETE. POST usa apenas a rota base e recebe a chave composta no objeto `id`.

### Parâmetros e payloads

- `id`: UUID no caminho das rotas de recursos simples.
- As rotas de relacionamento recebem dois UUIDs no caminho, com os nomes indicados na tabela acima.
- Cada controller possui seu próprio `record Request` no final do arquivo, mostrando exatamente o corpo aceito em POST e PUT.
- Os PATCHs foram limitados a ações simples, como alterar status, ativo ou habilitado.

As listagens retornam um array JSON simples. Essa versão prioriza uma estrutura didática; paginação pode ser adicionada quando a quantidade de dados exigir.

### Respostas de sucesso

As respostas retornam o recurso diretamente:

```json
{
  "id": "UUID",
  "name": "Exemplo",
  "status": "ACTIVE"
}
```

As listagens retornam um array e as exclusões usam `204 No Content`, sem corpo.

### Respostas de erro

Todos os erros seguem o mesmo contrato:

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

Principais status: `400` para payload ou parâmetro inválido, `404` para recurso inexistente,
`409` para duplicidade/conflito, `405` para verbo HTTP não suportado e `500` para falha inesperada.

## Exemplos

Criar plano:

```bash
curl -X POST http://localhost:8080/api/plans \
  -H 'Content-Type: application/json' \
  -d '{"name":"Profissional","defaultPrice":199.90,"durationDays":30,"status":"ACTIVE"}'
```

Atualizar somente o status:

```bash
curl -X PATCH http://localhost:8080/api/plans/UUID_DO_PLANO/status \
  -H 'Content-Type: application/json' \
  -d '{"status":"INACTIVE"}'
```

Vincular um módulo ao plano:

```bash
curl -X POST http://localhost:8080/api/plan-modules \
  -H 'Content-Type: application/json' \
  -d '{"planId":"UUID_DO_PLANO","moduleId":"UUID_DO_MODULO"}'
```

Os valores de enum são enviados em maiúsculas, conforme as classes em `domain`: por exemplo `ACTIVE`, `INACTIVE`, `TRIAL`, `OWNER` e `INVITED`.
