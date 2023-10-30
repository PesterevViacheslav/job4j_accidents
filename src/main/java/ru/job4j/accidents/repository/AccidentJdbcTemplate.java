package ru.job4j.accidents.repository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import java.sql.*;
import java.util.*;
/**
 * Class AccidentJdbcTemplate - Репозиторий хранения нарушений в БД. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private static final RowMapper<Accident> MAPPER = BeanPropertyRowMapper.newInstance(Accident.class);

    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
            connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(
                       "insert into accident (title, dsc, address, typ_id) values (?,?,?,?)",
                            new String[] {"id"});
                ps.setString(1, accident.getTitle());
                ps.setString(2, accident.getDsc());
                ps.setString(3, accident.getAddress());
                ps.setInt(4, accident.getType().getId());
                return ps;
            },
            keyHolder);
        accident.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        setAccidentRule(accident);
        return accident;
    }

    public boolean update(Accident accident, int id) {
        jdbc.update("update accident set title = ?, dsc = ?, address = ?, typ_id = ?"
                      + " where id = ?",
                accident.getTitle(),
                accident.getDsc(),
                accident.getAddress(),
                accident.getType().getId(),
                id
        );

        jdbc.update("DELETE FROM accident_rule "
                       + "WHERE accident_id = ?",
                    accident.getId()
        );

        setAccidentRule(accident);
        return true;
    }

    public List<Accident> getAll() {
        return jdbc.query(
              "select a.id, a.title, a.dsc, a.address,"
                 +      " a.typ_id, typ.title as typ_title,"
                 +      " r.id as rule_id, r.title as rule_title"
                 + " from accident a"
                 + " join typ on typ.id = a.typ_id"
                 + " left join accident_rule ar on ar.accident_id = a.id"
                 + " left join rule r on r.id = ar.rule_id"
                 + " order by a.id",
                (rs) -> {
            List<Accident> al = new ArrayList<>();
            while (rs.next()) {
                Accident accident = new Accident();
                accident.setId(rs.getInt("id"));
                accident.setTitle(rs.getString("title"));
                accident.setDsc(rs.getString("dsc"));
                accident.setAddress(rs.getString("address"));
                accident.setType(new AccidentType(rs.getInt(
                        "typ_id"),
                        rs.getString("typ_title")));
                accident.setRules(new HashSet<>());
                Rule rule = new Rule();
                rule.setId(rs.getInt("rule_id"));
                rule.setTitle(rs.getString("rule_title"));
                int id = al.indexOf(accident);
                if (id == -1) {
                    accident.getRules().add(rule);
                    al.add(accident);
                } else {
                    al.get(id).getRules().add(rule);
                }
            }
            return al;
        });
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(
                "select *  from accident a where a.id = ?", MAPPER, id)
        );
    }

    private void setAccidentRule(Accident accident) {
        List<Rule> rules = new ArrayList<>(accident.getRules());
        jdbc.batchUpdate("INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int val) throws SQLException {
                        Rule rule = rules.get(val);
                        ps.setInt(1, accident.getId());
                        ps.setInt(2, rule.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return rules.size();
                    }
                });
    }
}
