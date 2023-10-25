package ru.job4j.accidents.repository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import com.google.gson.Gson;
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

    public void setAccidentRule(Accident accident) {
        Gson gson = new Gson();
        jdbc.update("WITH val AS("
                        + "    SELECT ? AS acc_id"
                        + "    ), j AS (\n"
                        + "        SELECT\n"
                        + "            (res ->> 'id')::INT AS r_id\n"
                        + "        FROM json_array_elements(?::JSON) v(res)\n"
                        + "    ), dt AS (\n"
                        + "        SELECT\n"
                        + "             r.rule_id\n"
                        + "            ,j.r_id\n"
                        + "        FROM j CROSS JOIN val\n"
                        + "        FULL OUTER JOIN accident_rule r ON r.rule_id = j.r_id\n"
                        + "                                       AND r.accident_id = val.acc_id\n"
                        + "        WHERE COALESCE(r.accident_id, val.acc_id) = val.acc_id\n"
                        + "          AND (r.rule_id IS NULL OR j.r_id IS NULL)\n"
                        + "    ), del AS (\n"
                        + "        DELETE FROM accident_rule ar\n"
                        + "              WHERE ar.accident_id = (SELECT acc_id FROM val)\n"
                        + "                AND ar.rule_id IN (SELECT dt.rule_id\n"
                        + "                                     FROM dt\n"
                        + "                                    WHERE dt.rule_id IS NOT NULL\n"
                        + "                )\n"
                        + "        RETURNING ar.id\n"
                        + "    )\n"
                        + "        INSERT INTO accident_rule(accident_id, rule_id)\n"
                        + "            (SELECT\n"
                        + "                  val.acc_id AS accident_id\n"
                        + "                 ,dt.r_id\n"
                        + "             FROM dt CROSS JOIN val\n"
                        + "            WHERE dt.r_id IS NOT NULL\n"
                        + "            )\n"
                        + "",
                         accident.getId(),
                         gson.toJson(accident.getRules())
        );
    }
}
