package ru.job4j.accidents.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.List;
import java.util.Optional;
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
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    public List<Accident> getAll() {
        return accidentRepository.getAll();
    }

    public void create(Accident accident) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        accidentRepository.create(accident);
    }

    public boolean update(Accident accident, int id) {
        accident.setType(accidentTypeRepository.findById(accident.getType().getId()).get());
        return accidentRepository.update(accident, id);
    }

    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }
}
