package xyz.anclain.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnswerForm {

    private List<AnswerDTO> results;

    public AnswerForm() {}

}