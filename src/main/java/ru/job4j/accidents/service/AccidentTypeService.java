package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.*;

import java.util.List;

/**
 * Class AccidentTypeService - Сервис обработки типов нарушений. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeData accidentTypeJdbcTemplate;

    public List<AccidentType> getAll() {
        return accidentTypeJdbcTemplate.findAll();
    }
}
