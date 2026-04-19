package xyz.anclain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController {

    @GetMapping("/error_page")
    public String handleError() {
        return "error_page";
    }

}
