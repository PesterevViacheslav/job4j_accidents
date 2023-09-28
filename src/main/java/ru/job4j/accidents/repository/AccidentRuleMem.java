package ru.job4j.accidents.repository;
import ru.job4j.accidents.model.Rule;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;
/**
 * Class AccidentMem - Репозиторий хранения статей нарушений in memory. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Repository
public class AccidentRuleMem implements AccidentRuleRepository {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public AccidentRuleMem() {
        rules.put(1, new Rule(1, "Статья. 1"));
        rules.put(2, new Rule(2, "Статья. 2"));
        rules.put(3, new Rule(3, "Статья. 3"));
    }

    @Override
    public List<Rule> getAll() {
        return new ArrayList<>(rules.values());
    }

    @Override
    public Set<Rule> getRulesByAccidentIds(List<Integer> rIds) {
        Set<Rule> result = new HashSet<>();
        if (rIds != null) {
            for (Integer id : rIds) {
                Rule rule = rules.get(id);
                if (rule != null) {
                    result.add(rule);
                }
            }
        }
        return result;
    }
}