package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FirstController {

    @GetMapping("/hi")
    public String nicToMeetYou(Model model){
        model.addAttribute("username", "shinbogeun");
        return "greetings";  //templates/greetings.mustache -> 브라우저로 전송!
    }

    @GetMapping("/bye")
    public String bye(Model model){
        model.addAttribute("username", "bye");
        return "goodbye";
    }
}
