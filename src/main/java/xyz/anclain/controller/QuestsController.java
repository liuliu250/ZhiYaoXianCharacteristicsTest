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

    @GetMapping("")
    public String index() {
        return "quests_index"; // 返回上面创建的 HTML 文件名
    }

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
        double[] vectorSum = new double[5];
        double[] weightAbsSum = new double[5];
        List<Map<String, Object>> details = new ArrayList<>();

        if (form.getResults() != null) {
            for (AnswerDTO answer : form.getResults()) {
                Quests q = questsRepository.findById(answer.getId()).orElse(null);
                if (q != null && q.getPriority() != null) {
                    // 直接获取滑动条分值 (-1 到 1)
                    double userVal = answer.getScore();
                    List<Double> priority = q.getPriority();

                    for (int i = 0; i < 5; i++) {
                        if (i < priority.size()) {
                            double w = priority.get(i);
                            // 累加：分值 * 权重
                            vectorSum[i] += userVal * w;
                            // 分母：权重绝对值的累加
                            weightAbsSum[i] += Math.abs(w);
                        }
                    }

                    Map<String, Object> detail = new HashMap<>();
                    detail.put("id", q.getId());
                    detail.put("title", q.getTitle());
                    detail.put("userScore", userVal); // 存入 -1~1 的分值用于明细展示
                    details.add(detail);
                }
            }
        }

        // 计算最终向量
        double[] finalVector = new double[5];
        for (int i = 0; i < 5; i++) {
            finalVector[i] = (weightAbsSum[i] != 0) ? (vectorSum[i] / weightAbsSum[i]) : 0.0;
        }

        model.addAttribute("finalVector", finalVector);
        model.addAttribute("details", details);
        return "quests_stats";
    }

}