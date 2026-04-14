package xyz.anclain.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.anclain.dto.AnswerDTO;
import xyz.anclain.dto.AnswerForm;
import xyz.anclain.entity.Quests;
import xyz.anclain.repository.QuestsRepository;
import xyz.anclain.utils.DimensionReference;
import xyz.anclain.utils.QuestService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static xyz.anclain.utils.DimensionReference.NAMES;

@Controller
@RequestMapping("/quests")
public class QuestsController {

    @Autowired
    private QuestService questService;
    @Autowired
    private QuestsRepository questsRepository;

    @GetMapping("")
    public String index() {
        return "quests_index"; // 返回上面创建的 HTML 文件名
    }

    @GetMapping("/list")
    public String getAllQuests(Model model) {
        model.addAttribute("questsList", questsRepository.findByIsShownTrue());
        return "quests_list";
    }

    @GetMapping("/create")
    public String createQuests(Model model) {
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
        }

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

        // 1. 调用 Service 计算向量
        double[] finalVector = questService.calculateFinalVector(form.getResults(), questsRepository);

        // 2. 调用 Service 匹配人格
        String matchedProfile = questService.findBestMatchProfile(finalVector);

        // 3. 准备前端展示数据
        model.addAttribute("vectorDetails", questService.buildVectorDetails(finalVector));
        model.addAttribute("matchedProfile", matchedProfile);
        model.addAttribute("dimensionProfiles", DimensionReference.PROFILES);
        model.addAttribute("details", questService.buildAnswerDetails(form.getResults(), questsRepository));

        return "quests_stats";
    }

}