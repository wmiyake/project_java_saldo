sqlCREATE TABLE lancamentos (
    id                  UUID            PRIMARY KEY,
    tipo                VARCHAR(10)     NOT NULL CHECK (tipo IN ('CREDITO', 'DEBITO')),
    valor               NUMERIC(19,2)   NOT NULL CHECK (valor > 0),
    descricao           VARCHAR(255)    NOT NULL,
    data_hora           TIMESTAMP       NOT NULL,
    chave_idempotencia  VARCHAR(100)    NOT NULL UNIQUE,
    criado_em           TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- TRIGGER: bloqueia UPDATE e DELETE no banco
CREATE OR REPLACE FUNCTION fn_bloquear_lancamento()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Lançamentos são imutáveis. % não permitido.', TG_OP;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_bloquear_update
    BEFORE UPDATE ON lancamentos FOR EACH ROW
    EXECUTE FUNCTION fn_bloquear_lancamento();

CREATE TRIGGER trg_bloquear_delete
    BEFORE DELETE ON lancamentos FOR EACH ROW
    EXECUTE FUNCTION fn_bloquear_lancamento();

CREATE TABLE saldo_consolidado (
                                   id                      BIGSERIAL   PRIMARY KEY,
                                   data                    DATE        NOT NULL UNIQUE,
                                   total_creditos          NUMERIC(19,2) NOT NULL,
                                   total_debitos           NUMERIC(19,2) NOT NULL,
                                   saldo                   NUMERIC(19,2) NOT NULL,
                                   quantidade_lancamentos  INT         NOT NULL,
                                   consolidado_em          TIMESTAMP   NOT NULL,
                                   status                  VARCHAR(20) NOT NULL
);