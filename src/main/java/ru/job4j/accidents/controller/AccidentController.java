package ru.job4j.accidents.controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

/**
 * Class AccidentController - Контроллер обработки нарушений. Решение задач уровня Middle.
 * Категория : 3.5. Spring boot. Тема : 3.4.2. MVC
 *
 * @author Viacheslav Pesterev (pesterevvv@gmail.com)
 * @since 15.08.2023
 * @version 1
 */
@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/formCreateAccident")
    public String viewCreateAccident() {
        return "accidents/createAccident";
    }

    @GetMapping("/formEditAccident")
    public String viewEditAccident(@RequestParam int id, Model model) {
        Optional<Accident> accident = accidents.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/accidents/fail";
        } else {
            model.addAttribute("accident", accident.get());
            return "accidents/editAccident";
        }
    }

    @GetMapping("/fail")
    public String fail() {
        return "accidents/fail";
    }

    @PostMapping("/createAccident")
    public String create(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/index";
    }

    @PostMapping("/editAccident")
    public String edit(@ModelAttribute Accident accident, @RequestParam(value = "id") int recId) {
        accidents.update(accident, recId);
        return "redirect:/index";
    }
}