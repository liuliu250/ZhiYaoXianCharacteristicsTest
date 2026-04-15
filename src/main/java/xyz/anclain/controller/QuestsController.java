package xyz.anclain.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.anclain.dto.AnswerForm;
import xyz.anclain.entity.Quests;
import xyz.anclain.entity.TestResult;
import xyz.anclain.repository.QuestsRepository;
import xyz.anclain.repository.TestResultRepository;
import xyz.anclain.utils.DimensionReference;
import xyz.anclain.utils.QuestService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static xyz.anclain.utils.DimensionReference.DIMENSIONS;

@Controller
@RequestMapping("/quests")
public class QuestsController {

    @Autowired
    private QuestService questService;
    @Autowired
    private QuestsRepository questsRepository;
    @Autowired
    private TestResultRepository testResultRepository;

    @GetMapping("")
    public String index() {
        return "quests_index";
    }

    @GetMapping("/list")
    public String getAllQuests(Model model) {
        model.addAttribute("questsList", questsRepository.findByIsShownTrue());
        model.addAttribute("dimensions", DimensionReference.DIMENSIONS);
        return "quests_list";
    }

    @GetMapping("/create")
    public String createQuests(Model model) {
        model.addAttribute("dimensions", DIMENSIONS);
        return "quests_create";
    }

    @PostMapping("/save")
    public String saveQuests(Quests quest, @RequestParam(value = "priority", required = false) List<Double> priority) {
        quest.setCreateTime(LocalDateTime.now());
        quest.setIsShown(true);

        if (priority != null) {
            List<Double> cleanedList = priority.stream()
                    .filter(Objects::nonNull)
                    .map(val -> val < -1 ? -1.0 : val)
                    .map(val -> val > 1 ? 1.0 : val)
                    .collect(Collectors.toList());
            quest.setPriority(cleanedList);
        } else {return List.of(0, 0, 0, 0, 0).toString();}

        questsRepository.save(quest);
        return "redirect:/quests/list";
    }

    @GetMapping("/answer")
    public String startAnswering(Model model) {
        List<Quests> allQuests = questsRepository.findByIsShownTrue();
         Collections.shuffle(allQuests);
        // List<Quests> selected = allQuests.stream().limit(30).collect(Collectors.toList());

        model.addAttribute("questsList", allQuests);
        return "quests_answer";
    }



    @PostMapping("/stats")
    public String calculateStats(AnswerForm form, Model model) {
        if (form.getResults() == null) return "redirect:/quests";

        double[] finalVector = questService.calculateFinalVector(form.getResults(), questsRepository);

        QuestService.MatchResult result = questService.findBestMatchProfile(finalVector);

        model.addAttribute("vectorDetails", questService.buildVectorDetails(finalVector));
        model.addAttribute("matchedProfile", result.name);
        model.addAttribute("matchedScore", String.format("%.2f", result.cosineSimilarity * 100) + "%");
        model.addAttribute("dimensionProfiles", DimensionReference.PROFILES);
        model.addAttribute("details", questService.buildAnswerDetails(form.getResults(), questsRepository));

        return "quests_stats";
    }

}