package xyz.anclain.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.anclain.dto.AnswerDTO;
import xyz.anclain.entity.TestResult;
import xyz.anclain.repository.QuestsRepository;
import xyz.anclain.repository.TestResultRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestService {

    public static class MatchResult {
        public String name;
        public double cosineSimilarity;

        public MatchResult(String name, double cosineSimilarity) {
            this.name = name;
            this.cosineSimilarity = cosineSimilarity;
        }
    }

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

    public MatchResult findBestMatchProfile(double[] userVector) {
        String bestMatchName = "未知";
        double minDistance = Double.MAX_VALUE;
        double[] bestMatchScores = null;

        for (DimensionReference.StandardProfile profile : DimensionReference.PROFILES) {
            double dist = 0;
            for (int i = 0; i < 5; i++) {
                dist += Math.pow(userVector[i] - profile.scores[i], 2);
            }

            if (dist < minDistance) {
                minDistance = dist;
                bestMatchName = profile.name;
                bestMatchScores = profile.scores;
            }
        }

        double cosineSim = 0;
        if (bestMatchScores != null) {
            cosineSim = calculateCosineSimilarity(userVector, bestMatchScores);
        }

        return new MatchResult(bestMatchName, cosineSim);
    }

    public List<Map<String, Object>> buildVectorDetails(double[] vector) {
        List<Map<String, Object>> details = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> map = new HashMap<>();
            double val = vector[i];

            DimensionReference.DimensionMeta meta = DimensionReference.DIMENSIONS.get(i);
            map.put("name", meta.name);
            map.put("value", val);
            map.put("comment", meta.getComment(val));

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

    public static double calculateCosineSimilarity(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) return 0;

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.length; i++) {
            dotProduct += vectorA[i] * vectorB[i];
            normA += Math.pow(vectorA[i], 2);
            normB += Math.pow(vectorB[i], 2);
        }

        double denominator = Math.sqrt(normA) * Math.sqrt(normB);
        if (denominator == 0) return 0;

        double cosine = dotProduct / denominator;

        return (cosine + 1) / 2;
    }

    @Autowired
    private TestResultRepository testResultRepository;

    public void saveFinalResult(double[] finalVector, MatchResult result) {
        TestResult testResult = new TestResult();
        testResult.setVector(finalVector);
        testResult.setMatchedProfile(result.name);
        testResult.setMatchedScore(result.cosineSimilarity);
        testResult.setCreatedAt(LocalDateTime.now());

        testResultRepository.save(testResult);
    }

}