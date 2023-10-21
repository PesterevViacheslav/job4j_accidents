CREATE OR REPLACE FUNCTION accident_rule_set(
     i_accident_id INTEGER
    ,i_rules JSON
) RETURNS INTEGER
AS $$
DECLARE
    vres INTEGER;
BEGIN
    WITH j AS (
        SELECT
            (res ->> 'id')::INT AS r_id
        FROM json_array_elements(i_rules) v(res)
    ), dt AS (
        SELECT
             r.rule_id
            ,j.r_id
        FROM j
        FULL OUTER JOIN accident_rule r ON r.rule_id = j.r_id
                                       AND r.accident_id = i_accident_id
        WHERE COALESCE(r.accident_id, i_accident_id) = i_accident_id
          AND (r.rule_id IS NULL OR j.r_id IS NULL)
    ), del AS (
        DELETE FROM accident_rule ar
              WHERE ar.accident_id = i_accident_id
                AND ar.rule_id IN (SELECT dt.rule_id
                                     FROM dt
                                    WHERE dt.rule_id IS NOT NULL
                )
        RETURNING ar.id
    ), ins AS (
        INSERT INTO accident_rule(accident_id, rule_id)
            (SELECT
                  i_accident_id AS accident_id
                 ,dt.r_id
             FROM dt
            WHERE dt.r_id IS NOT NULL
            )
            RETURNING id
    )
    SELECT
        ((SELECT count(*) FROM del) + (SELECT count(*) FROM ins)) INTO vres
    ;
    RETURN vres;
END;
$$ LANGUAGE plpgsql;