package xyz.anclain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.anclain.entity.Quests;
import xyz.anclain.repository.QuestsRepository;

import java.time.LocalDateTime;

@RestController
public class DefaultController {

    @GetMapping("")
    public String init() {
        return "Hello, World!";
    }

    @GetMapping("/login")
    public String login() {
        return "Hello, World!\nThis is login page.";
    }

    @Autowired
    private QuestsRepository questsRepository;

    @PostMapping("/quests/create")
    public String createSurvey(@RequestParam String title, @RequestParam String author) {
        Quests q = new Quests();
        q.setTitle(title);
        q.setAuthor(author);
        q.setCreateTime(LocalDateTime.now());

        questsRepository.save(q);
        return "Success!, Quest Num is " + q.getId();
    }

}