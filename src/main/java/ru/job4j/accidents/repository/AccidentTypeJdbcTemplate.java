package ru.job4j.accidents.repository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Optional;
/**
 * Class AccidentTypeJdbcTemplate - Репозиторий хранения нарушений в БД. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;
    private static final RowMapper<AccidentType> MAPPER = BeanPropertyRowMapper.newInstance(AccidentType.class);

    @Override
    public List<AccidentType> getAll() {
        return jdbc.query("SELECT * FROM typ order by id", MAPPER);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject("SELECT * FROM typ WHERE id = ?", MAPPER, id));
    }
}