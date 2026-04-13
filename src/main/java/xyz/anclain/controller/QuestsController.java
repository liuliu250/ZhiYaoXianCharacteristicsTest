package xyz.anclain.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.anclain.entity.Quests;
import xyz.anclain.repository.QuestsRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quests")
public class QuestsController {

    @Autowired
    private QuestsRepository questsRepository;

    @GetMapping("/list")
    public String getAllQuests(Model model) {
        model.addAttribute("questsList", questsRepository.findAll());
        return "quests_list";
    }

    @GetMapping("/create")
    public String createQuests(Model model) {
        return "quests_create";
    }

    @PostMapping("/save")
    public String saveQuests(Quests quest, @RequestParam(value = "priority", required = false) List<Double> priority) {
        quest.setCreateTime(LocalDateTime.now());

        if (priority != null) {
            List<Double> cleanedList = priority.stream()
                    .filter(Objects::nonNull)
                    .map(val -> val < 0 ? 0.0 : val)
                    .map(val -> val > 1 ? 1.0 : val)
                    .collect(Collectors.toList());
            quest.setPriority(cleanedList);
        }

        questsRepository.save(quest);
        return "redirect:/quests/list";
    }

}