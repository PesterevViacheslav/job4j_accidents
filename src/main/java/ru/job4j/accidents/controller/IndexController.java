package ru.job4j.accidents.controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import ru.job4j.accidents.service.AccidentService;
/**
 * Class IndexController - Контроллер обработки главной страницы. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Controller
@AllArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.getAll());
        return "index";
    }
}