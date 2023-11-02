package ru.job4j.accidents.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;
import java.util.*;
/**
 * Class AccidentService - Сервис обработки нарушений. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentData accidentRepository;
    private final AccidentTypeData accidentTypeRepository;
    private final AccidentRuleData accidentRuleRepository;

    public Iterable<Accident> getAll() {
        return accidentRepository.findAll();
    }

    public void create(Accident accident, List<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(new HashSet<>((Collection) accidentRuleRepository.findAllById(rIds)));
        accidentRepository.save(accident);
    }

    public boolean update(Accident accident, int id, List<Integer> rIds) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accident.setRules(new HashSet<>((Collection) accidentRuleRepository.findAllById(rIds)));
        accidentRepository.save(accident);
        return true;
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }
}
