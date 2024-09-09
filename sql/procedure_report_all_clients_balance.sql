-- procedure para gerar o relatorio de saldo de todos os clientes

CREATE OR REPLACE PROCEDURE REPORT_BALANCE_ALL_CLIENTS (
    P_CURSOR OUT SYS_REFCURSOR
)
AS
BEGIN
    -- faz a busca de todos os clientes ativos e retorna no cursor
    OPEN P_CURSOR FOR
        SELECT C.NAME AS CLIENTE_NOME,
               C.CREATED_AT AS DATA_CADASTRO,
               A.BALANCE AS SALDO_ATUAL
        FROM CLIENT C
        JOIN ACCOUNT A ON C.ID = A.CLIENT_ID
        WHERE A.ACTIVE = 1 AND C.ACTIVE = 1;
END;
