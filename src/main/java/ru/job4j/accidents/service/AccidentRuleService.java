package ru.job4j.accidents.service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRuleData;
/**
 * Class AccidentRuleService - Сервис обработки статей нарушений. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Service
@AllArgsConstructor
public class AccidentRuleService {
    private final AccidentRuleData accidentRuleRepository;

    public Iterable<Rule> getAll() {
        return accidentRuleRepository.findAll();
    }
}