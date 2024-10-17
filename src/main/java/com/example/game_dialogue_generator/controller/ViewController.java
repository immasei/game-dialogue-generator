package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "auth";
    }

    @GetMapping("/home")
//     prompt
    public String home() {
        return "home";
    }

    @GetMapping("/output")
    public String output() {return "output";}

    // Pass OutputMessage to output by OutputMessage id
    @GetMapping("/output/{id}")
    public String outputId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "output";
    }

    // Pass OutputMessage to output directly (not working)
    @PostMapping("/output/message")
    public String outputMessage(@RequestBody OutputMessageDTO outputMessage, Model model) {
        model.addAttribute("outputMessage", outputMessage);
        return "output";
    }

    @GetMapping("/archive")
    public String archive() {
        return "archive";
    }

}
