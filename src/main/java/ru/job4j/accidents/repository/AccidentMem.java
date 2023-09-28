package ru.job4j.accidents.repository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

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
    private final AtomicInteger id = new AtomicInteger(0);

    public AccidentMem() {
        Set<Rule> rules  = Set.of(new Rule(1, "Статья. 1"),
                                  new Rule(3, "Статья. 3"));
        Set<Rule> rules2 = Set.of(new Rule(2, "Статья. 2"));
        create(new Accident(0, "accident_1", "text_1", "address_1",
                             new AccidentType(1, "Две машины"), rules));
        create(new Accident(0, "accident_2", "text_2", "address_2",
                             new AccidentType(2, "Машина и человек"), rules2));
    }

    public List<Accident> getAll() {
        return new ArrayList<>(accidents.values());
    }

    public void create(Accident accident) {
        accident.setId(id.incrementAndGet());
        accidents.put(accident.getId(), accident);
    }

    public boolean update(Accident accident, int id) {
        return accidents.computeIfPresent(accident.getId(),
                (k, oldVacancy) -> new Accident(oldVacancy.getId(),
                                                accident.getName(),
                                                accident.getText(),
                                                accident.getAddress(),
                                                accident.getType(),
                                                accident.getRules()
                                                )
        ) != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }
}