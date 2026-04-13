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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quests")
public class QuestsController {

    @Autowired
    private QuestsRepository questsRepository;

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
                    .map(val -> val < 0 ? 0.0 : val)
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
        // 初始化一个长度为 5 的数组，用于累加每个维度的总分
        double[] vectorSum = new double[5];
        List<Map<String, Object>> details = new ArrayList<>();
        int questCount = 0;

        for (AnswerDTO answer : form.getResults()) {
            Quests q = questsRepository.findById(answer.getId()).orElse(null);
            if (q != null && q.getPriority() != null) {
                questCount++;
                double userSliderScale = answer.getScore() / 100.0; // 将 0-100 转为 0-1
                List<Double> priority = q.getPriority();

                // 题目最终分值向量 = 用户分值 * 权重向量 (逐位相乘)
                // 同时累加到总向量中
                for (int i = 0; i < 5; i++) {
                    if (i < priority.size()) {
                        vectorSum[i] += userSliderScale * priority.get(i);
                    }
                }

                // 封装明细展示
                Map<String, Object> detail = new HashMap<>();
                detail.put("id", q.getId());
                detail.put("title", q.getTitle());
                detail.put("userScore", answer.getScore());
                details.add(detail);
            }
        }

        // 计算均值向量：逐分量除以题目总数
        double[] avgVector = new double[5];
        if (questCount > 0) {
            for (int i = 0; i < 5; i++) {
                avgVector[i] = vectorSum[i] / questCount;
            }
        }

        model.addAttribute("finalVector", avgVector); // 传递 5 维结果向量
        model.addAttribute("details", details);
        return "quests_stats";
    }

}