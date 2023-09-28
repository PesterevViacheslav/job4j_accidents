package ru.job4j.accidents.repository;
import ru.job4j.accidents.model.Rule;
import java.util.List;
import java.util.Set;
public interface AccidentRuleRepository {
    List<Rule> getAll();

    Set<Rule> getRulesByAccidentIds(List<Integer> rIds);
}