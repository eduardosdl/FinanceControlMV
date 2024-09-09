-- gatilho para calcular o saldo final de acordo com as transações inseridas

create or replace TRIGGER update_account_balance
AFTER INSERT ON transaction
FOR EACH ROW
DECLARE
    v_balance account.balance%TYPE;  -- armazena o saldo da conta
BEGIN
    SELECT balance INTO v_balance
    FROM account
    WHERE id = :NEW.account_id;

    -- verifica o tipo da operação
    IF :NEW.operation_type = 'C' THEN
        v_balance := v_balance + :NEW.amount;
    ELSIF :NEW.operation_type = 'D' THEN
        v_balance := v_balance - :NEW.amount;
    END IF;

    UPDATE account
    SET balance = v_balance
    WHERE id = :NEW.account_id;

END;