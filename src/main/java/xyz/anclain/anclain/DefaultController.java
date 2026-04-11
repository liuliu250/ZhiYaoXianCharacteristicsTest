package xyz.anclain.anclain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("")
    public String init() {
        return "Hello, World!";
    }

}
