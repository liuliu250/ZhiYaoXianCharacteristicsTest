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
        dimMap4.put(-1.0, "穷鬼");//[-1, -0.6)
        dimMap4.put(-0.6, "中产");//[-0.6, -0.15)
        dimMap4.put(-0.15, "小资");//[-0.15, 0.15)
        dimMap4.put(0.15, "富哥");//[0.15, 0.6)
        dimMap4.put(0.6, "地头蛇");//[0.6, 1]
        DIMENSIONS.add(new DimensionMeta("出生背景", dimMap4));

        TreeMap<Double, String> dimMap5 = new TreeMap<>();
        dimMap5.put(-1.0, "玩世不恭");//[-1, -0.6)
        dimMap5.put(-0.6, "乐子");//[-0.6, -0.15)
        dimMap5.put(-0.15, "日子人");//[-0.15, 0.15)
        dimMap5.put(0.15, "古板");//[0.15, 0.6)
        dimMap5.put(0.6, "战争机器");//[0.6, 1]
        DIMENSIONS.add(new DimensionMeta("严肃性", dimMap5));
    }

    public static final List<StandardProfile> PROFILES = Arrays.asList(
            new StandardProfile("指挥官", new double[]{0.1, 0.7, 0.2, -0.1, 0.4}),
            new StandardProfile("调停者", new double[]{0.3, 0.2, 0.9, 0.5, 0.4}),
            new StandardProfile("研究员", new double[]{0.9, 0.9, 0.1, 0.3, 0.6})
    );

}
