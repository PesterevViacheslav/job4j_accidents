package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class AccidentHibernate - Репозиторий хранения нарушений в БД (Hibernate). Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {
    private final CrudRepository crudRepository;

    @Override
    public Accident create(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    @Override
    public List<Accident> getAll() {
        return crudRepository.query(
                """
                        SELECT
                            DISTINCT a 
                        FROM Accident a
                        JOIN FETCH a.rules r
                        ORDER BY a.id
                        """,
                Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                """
                        FROM Accident a
                        WHERE a.id = :id
                        """,
                Accident.class,
                Map.of("id", id));
    }

    @Override
    public boolean update(Accident accident, int id) {
        crudRepository.run(session -> session.merge(accident));
        return true;
    }
}