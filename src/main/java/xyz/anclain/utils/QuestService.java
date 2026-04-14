package xyz.anclain.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.anclain.dto.AnswerDTO;
import xyz.anclain.repository.QuestsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestService {

    public double[] calculateFinalVector(List<AnswerDTO> results, QuestsRepository repository) {
        double[] vectorSum = new double[5];
        double[] weightAbsSum = new double[5];

        for (AnswerDTO answer : results) {
            repository.findById(answer.getId()).ifPresent(q -> {
                List<Double> priority = q.getPriority();
                double userVal = answer.getScore();
                for (int i = 0; i < 5; i++) {
                    if (i < priority.size()) {
                        double w = priority.get(i);
                        vectorSum[i] += userVal * w;
                        weightAbsSum[i] += Math.abs(w);
                    }
                }
            });
        }

        double[] finalVector = new double[5];
        for (int i = 0; i < 5; i++) {
            finalVector[i] = (weightAbsSum[i] != 0) ? (vectorSum[i] / weightAbsSum[i]) : 0.0;
        }
        return finalVector;
    }

    public String findBestMatchProfile(double[] userVector) {
        String bestMatch = "未知";
        double minDistance = Double.MAX_VALUE;

        for (DimensionReference.StandardProfile profile : DimensionReference.PROFILES) {
            double dist = 0;
            for (int i = 0; i < 5; i++) {
                dist += Math.pow(userVector[i] - profile.scores[i], 2);
            }
            if (dist < minDistance) {
                minDistance = dist;
                bestMatch = profile.name;
            }
        }
        return bestMatch;
    }

    public List<Map<String, Object>> buildVectorDetails(double[] vector) {
        List<Map<String, Object>> details = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            double val = vector[i];
            map.put("name", DimensionReference.NAMES.get(i));
            map.put("value", val);
            map.put("comment", DimensionReference.getComment(val));
            details.add(map);
        }
        return details;
    }

    public List<Map<String, Object>> buildAnswerDetails(List<AnswerDTO> results, QuestsRepository repository) {
        List<Map<String, Object>> details = new ArrayList<>();
        if (results == null) return details;

        for (AnswerDTO answer : results) {
            Map<String, Object> map = new HashMap<>();
            // 从数据库查出完整的题目信息
            repository.findById(answer.getId()).ifPresent(q -> {
                map.put("id", q.getId());
                map.put("title", q.getTitle()); // 确保这里存入了 title
                map.put("userScore", answer.getScore());
                details.add(map);
            });
        }
        return details;
    }
}