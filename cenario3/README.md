# Cenário 3 — Sistema de Escola de Cursos Livres


## Tabelas Identificadas com Campos Mínimos

### 1. aluno
| Coluna    | Tipo         | Restrições             |
|-----------|--------------|------------------------|
| id        | SERIAL       | PRIMARY KEY            |
| nome      | VARCHAR(100) | NOT NULL               |
| email     | VARCHAR(150) | NOT NULL               |
| telefone  | VARCHAR(20)  | NOT NULL               |

### 2. curso
| Coluna             | Tipo         | Restrições                |
|--------------------|--------------|---------------------------|
| id                 | SERIAL       | PRIMARY KEY               |
| nome               | VARCHAR(100) | NOT NULL                  |
| descricao          | TEXT         |                           |
| carga_horaria      | INTEGER      | NOT NULL, > 0             |
| vagas_totais       | INTEGER      | NOT NULL, > 0             |
| vagas_disponiveis  | INTEGER      | NOT NULL, >= 0            |

### 3. matricula (MOVIMENTO)
| Coluna          | Tipo    | Restrições                                     |
|-----------------|---------|------------------------------------------------|
| id              | SERIAL  | PRIMARY KEY                                    |
| id_aluno        | INTEGER | NOT NULL, FK → aluno(id)                       |
| id_curso        | INTEGER | NOT NULL, FK → curso(id)                       |
| data_matricula  | DATE    | NOT NULL                                       |
| valor           | NUMERIC | NOT NULL, >= 0                                 |

---

## Comandos CREATE TABLE (SQL — PostgreSQL)

```sql
-- Criar banco de dados (executar uma vez)
CREATE DATABASE escola_cursos;

-- =============================================
-- Tabela: aluno
-- =============================================
CREATE TABLE aluno (
    id        SERIAL        PRIMARY KEY,
    nome      VARCHAR(100)  NOT NULL,
    email     VARCHAR(150)  NOT NULL,
    telefone  VARCHAR(20)   NOT NULL
);

-- =============================================
-- Tabela: curso
-- =============================================
CREATE TABLE curso (
    id                 SERIAL        PRIMARY KEY,
    nome               VARCHAR(100)  NOT NULL,
    descricao          TEXT,
    carga_horaria      INTEGER       NOT NULL CHECK (carga_horaria > 0),
    vagas_totais       INTEGER       NOT NULL CHECK (vagas_totais > 0),
    vagas_disponiveis  INTEGER       NOT NULL CHECK (vagas_disponiveis >= 0)
);

-- =============================================
-- Tabela: matricula (MOVIMENTO)
-- =============================================
CREATE TABLE matricula (
    id              SERIAL   PRIMARY KEY,
    id_aluno        INTEGER  NOT NULL REFERENCES aluno(id),
    id_curso        INTEGER  NOT NULL REFERENCES curso(id),
    data_matricula  DATE     NOT NULL,
    valor           NUMERIC(10,2) NOT NULL CHECK (valor >= 0),
    UNIQUE (id_aluno, id_curso)   -- impede matrícula duplicada no banco
);
```

---

## Regras de Negócio

| Código | Regra                                                                                             | Onde é validada       |
|--------|---------------------------------------------------------------------------------------------------|-----------------------|
| RN01   | Não é possível matricular um aluno que não esteja cadastrado no sistema.                          | MatriculaService      |
| RN02   | Não é possível matricular em um curso que não esteja cadastrado no sistema.                       | MatriculaService      |
| RN03   | Não é possível abrir uma matrícula em um curso que já atingiu o limite de vagas disponíveis.      | MatriculaService      |
| RN04   | O mesmo aluno não pode ser matriculado duas vezes no mesmo curso.                                 | MatriculaService + DB |
| RN05   | O valor pago na matrícula não pode ser negativo.                                                  | MatriculaService      |
| RN06   | Ao realizar uma matrícula com sucesso, o campo `vagas_disponiveis` do curso é decrementado em 1. | MatriculaService      |
| RN07   | O sistema deve permitir consultar todos os cursos em que um aluno está matriculado.               | MatriculaRepository   |
| RN08   | O sistema deve permitir consultar todos os alunos matriculados em um determinado curso.           | MatriculaRepository   |

---

