package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "auth";
    }

    @GetMapping("/home")
    // prompt
//    public String home() {
//        return "home";
//    }
    public String home(ModelMap model) {
        // TODO: Find username of logged-in password
        model.addAttribute("user", "defaultusername");
        return "home";
    }

    @GetMapping("/output")
    public String output() {return "output";}

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

}
