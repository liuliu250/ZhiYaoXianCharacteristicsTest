package xyz.anclain.dto; // 记得修改为你的实际包名

import lombok.Data;

@Data
public class AnswerDTO {

    private Long id;
    private Double score;

    // 必须有无参构造函数
    public AnswerDTO() {}

}