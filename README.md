# Sistema de Gestão de Ocorrências Escolares — Checkpoint 6

**Disciplina:** Arquitetura SOA e Web Services  
**Professor:** Carlos Eduardo Machado de Oliveira  
**Tecnologia:** Java 17, Spring Boot 3, Spring Data JPA, Spring Security, JWT e H2.

## Integrantes
- Vinicius Henrique — RM556908
- Enzo Dias — RM558225
- Gustavo Pierre — RM558928
- Gabriel Belo — RM551669
- Laura Souza — RM556320

## Visão geral
API REST para gestão de ocorrências escolares. O sistema contempla autenticação por JWT, autorização por perfil, cadastro de alunos e turmas, matrícula N:N com histórico, categorias e tipos de ocorrência, registro e consulta de ocorrências, dashboard e relatórios.

## Requisitos atendidos
| Requisito | Implementação |
|---|---|
| RF01 Login e perfis | `POST /api/auth/login`, JWT e quatro perfis |
| RF02 Alunos | CRUD em `/api/alunos`, com foto por URL |
| RF03 Turmas | CRUD em `/api/turmas`, turno, ano e semestre |
| RF04 Vínculo aluno/turma | `/api/matriculas`, histórico e encerramento |
| RF05 Categorias | CRUD em `/api/categorias` |
| RF06 Tipos | CRUD em `/api/tipos-ocorrencia`, vinculados a categoria |
| RF07 Registrar ocorrência | `POST /api/ocorrencias`, exige professor e matrícula ativa |
| RF08 Consultar | `GET /api/ocorrencias` com filtros |
| RF09 Detalhar | `GET /api/ocorrencias/{id}` |
| RF13 Relatórios | `/api/relatorios/dashboard` e `/api/relatorios/ocorrencias` |

## Perfis e permissões
- **PROFESSOR:** registra, consulta ocorrências de todos os professores, mas altera e exclui somente as próprias ocorrências.
- **ANALISTA_QUALIDADE:** consulta todas as ocorrências e relatórios.
- **COORDENADOR:** consulta todas as ocorrências, dashboard e relatórios.
- **ADMINISTRATIVO:** gerencia usuários, alunos, turmas, matrículas, categorias e tipos.

As permissões são verificadas no servidor, não apenas na interface. O professor não consegue alterar/excluir ocorrência de outro usuário, mesmo chamando diretamente a API.

## Requisitos
- JDK 17 ou superior
- Maven 3.8+
- IDE opcional (IntelliJ IDEA, Eclipse ou VS Code)

## Como executar
Na pasta raiz do projeto:

```bash
mvn clean test
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Console H2: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:file:./data/ocorrencias`
  - Usuário: `sa`
  - Senha: deixar em branco

O banco H2 em modo arquivo mantém os dados entre reinicializações. O Hibernate cria/atualiza as tabelas automaticamente.

## Usuários iniciais para demonstração
| Perfil | Login | Senha |
|---|---|---|
| Administrativo | `admin` | `Admin@123` |
| Professor | `professor` | `Prof@123` |
| Analista | `analista` | `Analista@123` |
| Coordenador | `coordenador` | `Coord@123` |

**Segurança:** essas credenciais são exclusivamente para ambiente acadêmico local. Altere-as antes de qualquer publicação externa. A chave JWT padrão em `application.yml` também deve ser substituída em produção.

## Exemplos de uso

### 1. Login
```http
POST /api/auth/login
Content-Type: application/json

{"login":"professor","senha":"Prof@123"}
```
Resposta inclui `token`. Envie-o nas próximas chamadas:
```http
Authorization: Bearer SEU_TOKEN
```

### 2. Criar aluno (administrativo)
```http
POST /api/alunos
Authorization: Bearer TOKEN_ADMIN
Content-Type: application/json

{"nome":"Maria da Silva","dataNascimento":"2008-04-15","fotoUrl":"https://exemplo.com/foto.jpg"}
```

### 3. Criar turma
```http
POST /api/turmas
{"nome":"1º Ano A","turno":"MANHA","ano":2026,"semestre":2}
```

### 4. Matricular aluno na turma
```http
POST /api/matriculas
{"alunoId":1,"turmaId":1}
```

### 5. Registrar ocorrência (professor)
```http
POST /api/ocorrencias
Authorization: Bearer TOKEN_PROFESSOR
Content-Type: application/json

{"turmaId":1,"alunoId":1,"categoriaId":1,"tipoId":1,"data":"2026-10-29","hora":"08:15:00","descricao":"Descrição detalhada da ocorrência."}
```

### 6. Consultar com filtros
```http
GET /api/ocorrencias?alunoId=1&turmaId=1&inicio=2026-01-01&fim=2026-12-31
```

### 7. Dashboard (coordenador)
```http
GET /api/relatorios/dashboard?inicio=2026-01-01&fim=2026-12-31
```

## Endpoints
| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/auth/login` | Público |
| GET | `/api/auth/me` | Autenticado |
| GET/POST | `/api/alunos` | Consulta: autenticados; escrita: administrativo |
| GET/PUT/DELETE | `/api/alunos/{id}` | Consulta: autenticados; escrita: administrativo |
| GET/POST | `/api/turmas` | Consulta: autenticados; escrita: administrativo |
| GET/PUT/DELETE | `/api/turmas/{id}` | Consulta: autenticados; escrita: administrativo |
| POST | `/api/matriculas` | Administrativo |
| GET | `/api/turmas/{id}/alunos` | Autenticados |
| GET | `/api/alunos/{id}/historico-turmas` | Autenticados |
| GET/POST | `/api/categorias` | Consulta: autenticados; escrita: administrativo |
| GET/POST | `/api/tipos-ocorrencia` | Consulta: autenticados; escrita: administrativo |
| GET/POST | `/api/ocorrencias` | Consulta: professor/analista/coordenador; criação: professor |
| GET/PUT/DELETE | `/api/ocorrencias/{id}` | Detalhe: professor/analista/coordenador; edição/exclusão: professor proprietário |
| GET | `/api/relatorios/dashboard` | Coordenador |
| GET | `/api/relatorios/ocorrencias` | Analista/coordenador |

## Modelo de dados
- `usuarios` pertence a um perfil (perfil armazenado como enum).
- `alunos` e `turmas` se relacionam N:N por `matriculas`.
- `ocorrencias` referencia turma, aluno, professor, categoria e tipo.
- `tipos_ocorrencia` referencia uma categoria.
- Matrículas encerradas são preservadas para manter o histórico acadêmico.

## Tratamento de erros
A API retorna HTTP 400 para dados inválidos, 401 para ausência/invalidade de autenticação, 403 para perfil sem permissão e 404 para recurso inexistente. Mensagens de validação são retornadas em JSON.

## Observações de escopo
- O campo `fotoUrl` armazena uma URL de foto; upload binário de arquivos não está implementado.
- Relatórios são disponibilizados em JSON; exportação PDF/Excel não faz parte desta API.
- Não foram incluídos cursos, disciplinas, notas, frequência, professores por turma ou acesso de alunos/responsáveis, conforme limites definidos no enunciado.
- O ZIP fornecido contém um protótipo HTML/CSS/JS que chama uma API antiga em endereço externo. Ele foi analisado como referência visual/funcional, mas não foi conectado automaticamente a esta API, pois os contratos e rotas são diferentes. A documentação desta entrega descreve os endpoints REST atuais.

## Arquitetura
```text
Cliente REST / Swagger
        |
        v
Controllers REST
        |
        v
Spring Security + JWT + autorização por perfil
        |
        v
Regras de negócio e validações
        |
        v
Spring Data JPA / Hibernate
        |
        v
Banco H2 (arquivo local)
```
