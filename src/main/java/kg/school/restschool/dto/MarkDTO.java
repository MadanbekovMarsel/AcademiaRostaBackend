package kg.school.restschool.dto;


import jakarta.validation.constraints.NotEmpty;
import kg.school.restschool.entity.enums.Topic;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MarkDTO {

    private Long id;
    @NotEmpty
    private int correctAns;
    @NotEmpty
    private int totalQuest;
    @NotEmpty
    private Topic topic;
    @NotEmpty
    private LocalDate date;
}
