package ru.job4j.accidents.repository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class AccidentMem - Репозиторий хранения нарушений in memory. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
public class AccidentMem implements AccidentRepository {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger id;
    public AccidentMem() {
        accidents.put(1, new Accident(1, "accident_1", "text_1", "address_1"));
        accidents.put(2, new Accident(2, "accident_2", "text_2", "address_2"));
        id = new AtomicInteger(accidents.size());
    }
    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }
    public void create(Accident accident) {
        accident.setId(id.incrementAndGet());
        accidents.put(accident.getId(), accident);
    }
    public void update(Accident accident, int id) {
        accidents.put(id, accident);
    }
    public Optional<Accident> findById(int id) {
        return Optional.of(accidents.get(id));
    }
}