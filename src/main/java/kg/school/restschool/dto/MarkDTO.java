package kg.school.restschool.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Date;

@Data
public class MarkDTO {

    private Long id;
    @NotEmpty
    private int correctAnswers;
    @NotEmpty
    private int totalQuestions;
    @NotEmpty
    private String topic;
    @NotEmpty
    private Date date;
}
