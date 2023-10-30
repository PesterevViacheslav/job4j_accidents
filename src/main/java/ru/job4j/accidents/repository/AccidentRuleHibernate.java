package ru.job4j.accidents.repository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Class AccidentRuleHibernate - Репозиторий хранения правил в БД (Hibernate). Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@AllArgsConstructor
@Repository
public class AccidentRuleHibernate implements AccidentRuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Rule> getAll() {
        return crudRepository.query(
                """
                      FROM Rule r
                      """,
                Rule.class);
    }

    @Override
    public Set<Rule> getAccidentRule(List<Integer> rIds) {
        return new HashSet<>(crudRepository.query(
                                 """
                                 FROM Rule r
                                 WHERE id IN :rId
                                 """,
                                 Rule.class,
                                 Map.of("rId", rIds)));
    }
}