-- Создание процедуры для добавления ингредиента в рецепт
CREATE OR REPLACE PROCEDURE add_ingredient_to_receipt(
    p_receipt_id BIGINT,         -- ID рецепта
    p_ingredient_name VARCHAR,   -- Имя ингредиента
    p_amount DOUBLE PRECISION,   -- Количество ингредиента
    p_order_in_receipt INTEGER   -- Порядок в рецепте
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_ingredient_id BIGINT;
BEGIN
    -- Получаем ID ингредиента по его имени
    SELECT id INTO v_ingredient_id
    FROM ingredient
    WHERE name = p_ingredient_name;

    -- Проверяем, существует ли такой ингредиент
    IF v_ingredient_id IS NULL THEN
        RAISE EXCEPTION 'Ингредиент % не найден', p_ingredient_name;
    END IF;

    -- Вставляем ингредиент в таблицу receipt_ingredient
    INSERT INTO receipt_ingredient (order_in_receipt, ingredient_id, receipt_id, amount)
    VALUES (p_order_in_receipt, v_ingredient_id, p_receipt_id, p_amount);

    -- Вывод сообщения об успешном добавлении
    RAISE NOTICE 'Ингредиент % добавлен в рецепт с ID % (количество: %)',
        p_ingredient_name, p_receipt_id, p_amount;
END $$;

DO $$
DECLARE
    receipt_name_var VARCHAR := 'espresso';    -- имя рецепта
    receipt_id_var BIGINT;
BEGIN

    -- Извлекаем id рецепта по имени
    SELECT id INTO receipt_id_var
    FROM receipt
    WHERE name = receipt_name_var;

    call add_ingredient_to_receipt(receipt_id_var, 'coffee', 50, 1);

END $$;

DO $$
DECLARE
    receipt_name_var VARCHAR := 'americano';    -- имя рецепта
    receipt_id_var BIGINT;
BEGIN

-- Извлекаем id рецепта по имени
    SELECT id INTO receipt_id_var
    FROM receipt
    WHERE name = receipt_name_var;

call add_ingredient_to_receipt(receipt_id_var, 'coffee', 50, 1);
call add_ingredient_to_receipt(receipt_id_var, 'water', 150, 2);
END $$;

DO $$
DECLARE
    receipt_name_var VARCHAR := 'cappuccino';    -- имя рецепта
    receipt_id_var BIGINT;
BEGIN

    -- Извлекаем id рецепта по имени
    SELECT id INTO receipt_id_var
    FROM receipt
    WHERE name = receipt_name_var;

    call add_ingredient_to_receipt(receipt_id_var, 'coffee', 50, 1);
    call add_ingredient_to_receipt(receipt_id_var, 'sugar', 20, 2);
    call add_ingredient_to_receipt(receipt_id_var, 'milk', 200, 3);
END $$;
