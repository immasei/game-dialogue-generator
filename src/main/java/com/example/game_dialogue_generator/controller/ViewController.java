package com.example.game_dialogue_generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "auth";
    }

    @GetMapping("/home")
    // prompt
    public String home() {
        return "home";
    }

    @GetMapping("/output")
    public String output() {return "output";}

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }
}
