package ru.job4j.accidents.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class IndexController {

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }

}