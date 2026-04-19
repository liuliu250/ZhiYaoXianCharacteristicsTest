package xyz.anclain.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import xyz.anclain.dto.AnswerDTO;
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

    @PostMapping("/submit")
    public String submitTest(AnswerForm form, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        double[] finalVector = questService.calculateFinalVector(form.getResults(), questsRepository);
        QuestService.MatchResult result = questService.findBestMatchProfile(finalVector);

        TestResult saveRecord = questService.saveFinalResult(finalVector, result);
        List<Map<String, Object>> details = questService.buildAnswerDetails(form.getResults(), questsRepository);

        session.setAttribute("lastTestDetails", details);

        redirectAttributes.addAttribute("id", saveRecord.getId());

        return "redirect:/quests/stats";
    }

    @GetMapping("/stats")
    public String showStats(@RequestParam("id") Long id, Model model, HttpSession session) {
        if (id == null) {return "redirect:/error_page";}

        Optional<TestResult> recordOpt = testResultRepository.findById(id);

        if (recordOpt.isEmpty()) {return "redirect:/error_page";}

        TestResult record = recordOpt.get();

        double[] finalVector = new double[]{
                record.getDim1(), record.getDim2(), record.getDim3(), record.getDim4(), record.getDim5()
        };

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> details = (List<Map<String, Object>>) session.getAttribute("lastTestDetails");

        model.addAttribute("vectorDetails", questService.buildVectorDetails(finalVector));
        model.addAttribute("matchedProfile", record.getMatchedProfile());
        model.addAttribute("matchedScore", String.format("%.2f", record.getMatchedScore() * 100) + "%");
        model.addAttribute("dimensionProfiles", DimensionReference.PROFILES);
        model.addAttribute("details", details);

        return "quests_stats";
    }

}