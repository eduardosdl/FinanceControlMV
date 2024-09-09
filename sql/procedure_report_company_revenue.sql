-- procedure para gerar o relatorio de receita da empresa

CREATE OR REPLACE PROCEDURE REPORT_COMPANY_REVENUE (
    P_DATA_INICIO IN DATE,
    P_DATA_FIM IN DATE,
    P_CURSOR OUT SYS_REFCURSOR
)
AS
BEGIN
    -- faz a busca de todos os clientes calculando a quantidade de transações e valor pago dentro do periodo informado
    OPEN P_CURSOR FOR
        SELECT CLIENTE_NOME,
               QUANTIDADE_MOVIMENTACOES,
               CASE
                   WHEN QUANTIDADE_MOVIMENTACOES <= 10 THEN 1 * QUANTIDADE_MOVIMENTACOES
                   WHEN QUANTIDADE_MOVIMENTACOES > 10 AND QUANTIDADE_MOVIMENTACOES <= 20 THEN 0.75 * QUANTIDADE_MOVIMENTACOES
                   ELSE 0.50 * QUANTIDADE_MOVIMENTACOES
               END AS VALOR_MOVIMENTACOES
        FROM (
            SELECT C.NAME AS CLIENTE_NOME,
                   COUNT(T.ID) AS QUANTIDADE_MOVIMENTACOES
            FROM CLIENT C
            JOIN ACCOUNT A ON C.ID = A.CLIENT_ID
            JOIN TRANSACTION T ON A.ID = T.ACCOUNT_ID
            WHERE T.CREATED_AT BETWEEN P_DATA_INICIO AND P_DATA_FIM
            GROUP BY C.NAME
        );
END;