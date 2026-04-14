package xyz.anclain.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DimensionReference {

    public static final List<String> NAMES = Arrays.asList("创新能力", "逻辑思维", "团队协作", "抗压能力", "执行力");

    public static final TreeMap<Double, String> COMMENTS = new TreeMap<>();

    static {
        COMMENTS.put(-1.0, "极差");
        COMMENTS.put(-0.8, "差");
        COMMENTS.put(-0.5, "较差");
        COMMENTS.put(-0.1, "中等");
        COMMENTS.put(0.1,  "较好");
        COMMENTS.put(0.5,  "好");
        COMMENTS.put(0.8,  "极好");
    }

    public static class StandardProfile {
        public String name;
        public double[] scores; // 五个维度的标准分

        public StandardProfile(String name, double[] scores) {
            this.name = name;
            this.scores = scores;
        }
    }

    public static final List<StandardProfile> PROFILES = Arrays.asList(
            new StandardProfile("指挥官", new double[]{0.8, 0.9, 0.4, 0.7, 0.9}),
            new StandardProfile("调停者", new double[]{0.3, 0.2, 0.9, 0.5, 0.4}),
            new StandardProfile("研究员", new double[]{0.9, 0.9, 0.1, 0.3, 0.6})
    );

    public static String getComment(double score) {
        Map.Entry<Double, String> entry = COMMENTS.floorEntry(score);
        return (entry != null) ? entry.getValue() : "暂无评价";
    }

}
