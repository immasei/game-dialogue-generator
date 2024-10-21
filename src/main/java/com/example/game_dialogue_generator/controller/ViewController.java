package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OpenAIRequestDTO;
import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.OpenAIRequestService;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ViewController {
    @Autowired
    OutputMessageService outputMessageService;

    @Autowired
    OpenAIRequestService openAIRequestService;

    // login - signup page
    @GetMapping("/")
    public String index() {
        return "auth";
    }

    // user prompt - story generator page
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // output page - display 1 story output (by story id)
    @GetMapping("/dialogue/{id}")
    public String story(@PathVariable("id") Long id, Model model) {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userid = principle.getUserid();

        Optional<OutputMessageDTO> outputMessage = outputMessageService.findOutputMessageByIdAndUserId(id, userid);
        if (outputMessage.isEmpty())
            model.addAttribute("dialogue", null);
        else
            model.addAttribute("dialogue", outputMessage.get());
        return "dialogue";
    }

    @GetMapping("/archive")
    public String archive(Model model) {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userid = principle.getUserid();

        List<OpenAIRequestDTO> openAIRequests = openAIRequestService.findOpenAIRequestByUserId(userid);
        model.addAttribute("dialogues", openAIRequests);
        return "archive";
    }

}
