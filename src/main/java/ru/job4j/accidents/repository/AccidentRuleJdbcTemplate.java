package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.*;

/**
 * Class AccidentRuleJdbcTemplate - Репозиторий хранения статей нарушений в БД. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class AccidentRuleJdbcTemplate implements AccidentRuleRepository {
    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<Rule> MAPPER = BeanPropertyRowMapper.newInstance(Rule.class);

    @Override
    public List<Rule> getAll() {
        return jdbc.query("SELECT * FROM rule ORDER BY id", MAPPER);
    }

    @Override
    public Set<Rule> getAccidentRule(List<Integer> rIds) {
        MapSqlParameterSource prm = new MapSqlParameterSource();
        prm.addValue("rIds", rIds);
        return new HashSet<>(namedParameterJdbcTemplate.query("SELECT * FROM rule WHERE id IN (:rIds)", prm, MAPPER));
    }

}

