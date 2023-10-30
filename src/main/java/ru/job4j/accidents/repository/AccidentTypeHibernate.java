package ru.job4j.accidents.repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class AccidentTypeHibernate implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<AccidentType> getAll() {
        return crudRepository.query(
                """
                      FROM AccidentType
                      """,
                      AccidentType.class);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(
                """
                      FROM AccidentType
                      WHERE id = :id
                      """,
                      AccidentType.class,
                      Map.of("id", id));
    }
}
