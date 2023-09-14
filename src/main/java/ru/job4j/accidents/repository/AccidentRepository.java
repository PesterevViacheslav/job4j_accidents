package ru.job4j.accidents.repository;
import ru.job4j.accidents.model.Accident;
import java.util.List;
import java.util.Optional;
public interface AccidentRepository {
    List<Accident> getAll();

    void create(Accident accident);

    void update(Accident accident, int id);

    Optional<Accident> findById(int id);
}