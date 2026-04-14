package xyz.anclain.utils;

import java.util.*;

public class DimensionReference {

    public static class StandardProfile {
        public String name;
        public double[] scores; // 五个维度的标准分

        public StandardProfile(String name, double[] scores) {
            this.name = name;
            this.scores = scores;
        }
    }

    public static class DimensionMeta {
        public String name;
        public TreeMap<Double, String> rangeComments;

        public DimensionMeta(String name, TreeMap<Double, String> comments) {
            this.name = name;
            this.rangeComments = comments;
        }

        // 根据得分获取该维度专属的评价
        public String getComment(double score) {
            Map.Entry<Double, String> entry = rangeComments.floorEntry(score);
            return (entry != null) ? entry.getValue() : "暂无评价";
        }
    }

    public static final List<DimensionMeta> DIMENSIONS = new ArrayList<>();

    static {
        TreeMap<Double, String> dimMap1 = new TreeMap<>();
        dimMap1.put(-1.0, "男");//[-1, -0.2)
        dimMap1.put(-0.2, "中性");//[-0.2, 0.2)
        dimMap1.put(0.2, "女");//[0.2, 1]
        DIMENSIONS.add(new DimensionMeta("心理性别", dimMap1));

        TreeMap<Double, String> dimMap2 = new TreeMap<>();
        dimMap2.put(-1.0, "乖宝宝");//[-1, -0.6)
        dimMap2.put(-0.6, "随波逐流");//[-0.6, -0.15)
        dimMap2.put(-0.15, "引流狗");//[-0.15, 0.15)
        dimMap2.put(0.15, "冲浪高手");//[0.15, 0.6)
        dimMap2.put(0.6, "老资历");//[0.6, 1]
        DIMENSIONS.add(new DimensionMeta("亚文化浓度", dimMap2));

        TreeMap<Double, String> dimMap3 = new TreeMap<>();
        dimMap3.put(-1.0, "做题区");//[-1, -0.3)
        dimMap3.put(-0.3, "做题家");//[-0.3, 0.3)
        dimMap3.put(0.3, "做题神");//[0.3, 1]
        DIMENSIONS.add(new DimensionMeta("做题道德", dimMap3));

        TreeMap<Double, String> dimMap4 = new TreeMap<>();
        dimMap4.put(-1.0, "墙头草");//[-1, -0.6)
        dimMap4.put(-0.6, "社交达人");//[-0.6, -0.15)
        dimMap4.put(-0.15, "背景板");//[-0.15, 0.15)
        dimMap4.put(0.15, "小团体");//[0.15, 0.6)
        dimMap4.put(0.6, "内阁");//[0.6, 1]
        DIMENSIONS.add(new DimensionMeta("抱团能力", dimMap4));

        TreeMap<Double, String> dimMap5 = new TreeMap<>();
        dimMap5.put(-1.0, "自卑");//[-1, -0.6)
        dimMap5.put(-0.6, "谦虚");//[-0.6, -0.15)
        dimMap5.put(-0.15, "平庸");//[-0.15, 0.15)
        dimMap5.put(0.15, "自信");//[0.15, 0.6)
        dimMap5.put(0.6, "自大");//[0.6, 1]
        DIMENSIONS.add(new DimensionMeta("自我认知", dimMap5));
    }

    public static final List<StandardProfile> PROFILES = Arrays.asList(
            new StandardProfile("黄西轮", new double[]{-0.8, 0.35, -0.1, 0, 0.4}),
            new StandardProfile("鸡人", new double[]{-0.3, 0.15, -0.05, -0.2, 0.1}),
            new StandardProfile("水管", new double[]{-0.65, 0.6, 0.25, 0.15, 0.25}),
            new StandardProfile("牛至", new double[]{-0.75, 0.4, 0.4, 0.2, 0.3}),
            new StandardProfile("丁丁", new double[]{-0.7, -0.4, 0.1, -0.25, 0.1}),
            new StandardProfile("ycy", new double[]{-0.75, 0.15, 0.0, 0.1, -0.1}),
            new StandardProfile("kuigo", new double[]{-0.7, 0.1, 0.3, -0.15, -0.2})
    );

}
