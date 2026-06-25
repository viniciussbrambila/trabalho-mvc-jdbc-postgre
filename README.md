# Cenário 2 - Sistema de Oficina Mecânica

## Tabelas Identificadas

### cliente
| Campo    | Tipo         | Restrição   |
|----------|-------------|-------------|
| id       | SERIAL      | PRIMARY KEY |
| nome     | VARCHAR(100)| NOT NULL    |
| telefone | VARCHAR(20) | NOT NULL    |

### veiculo
| Campo      | Tipo         | Restrição                  |
|------------|-------------|----------------------------|
| id         | SERIAL      | PRIMARY KEY                |
| placa      | VARCHAR(10) | NOT NULL, UNIQUE           |
| modelo     | VARCHAR(100)| NOT NULL                   |
| ano        | INTEGER     | NOT NULL                   |
| id_cliente | INTEGER     | FK → cliente(id), NOT NULL |

### ordem_servico
| Campo      | Tipo          | Restrição                  |
|------------|--------------|----------------------------|
| id         | SERIAL       | PRIMARY KEY                |
| id_veiculo | INTEGER      | FK → veiculo(id), NOT NULL |
| descricao  | TEXT         | NOT NULL                   |
| valor      | NUMERIC(10,2)| NOT NULL, CHECK >= 0       |
| status     | VARCHAR(20)  | NOT NULL, DEFAULT 'ABERTA' |

## Comandos SQL

```sql
CREATE TABLE cliente (
    id       SERIAL       PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)  NOT NULL
);

CREATE TABLE veiculo (
    id         SERIAL       PRIMARY KEY,
    placa      VARCHAR(10)  NOT NULL UNIQUE,
    modelo     VARCHAR(100) NOT NULL,
    ano        INTEGER      NOT NULL,
    id_cliente INTEGER      NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE ordem_servico (
    id         SERIAL        PRIMARY KEY,
    id_veiculo INTEGER       NOT NULL,
    descricao  TEXT          NOT NULL,
    valor      NUMERIC(10,2) NOT NULL CHECK (valor >= 0),
    status     VARCHAR(20)   NOT NULL DEFAULT 'ABERTA',
    FOREIGN KEY (id_veiculo) REFERENCES veiculo(id)
);
```

## Regras de Negócio

1. **Não é permitido abrir uma ordem de serviço para um veículo não cadastrado.**
   → Validado em `OrdemServicoService.abrir()` consultando o banco

2. **O valor do serviço não pode ser negativo.**
   → Validado em `OrdemServicoService.abrir()` + reforçado com CHECK no banco

3. **Um cliente pode ter mais de um veículo cadastrado.**
   → Relação 1:N entre cliente e veículo (via id_cliente)

4. **Uma ordem de serviço pode estar ABERTA ou CONCLUIDA.**
   → Campo status; toda OS nasce com status 'ABERTA'

5. **É possível consultar o histórico completo de manutenções de um veículo.**
   → Método `listarPorVeiculo(int idVeiculo)` em `OrdemServicoRepository`

6. **É possível listar todos os veículos de um cliente específico.**
   → Método `listarPorCliente(int idCliente)` em `VeiculoRepository`
