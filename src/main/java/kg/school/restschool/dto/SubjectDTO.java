package kg.school.restschool.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SubjectDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int cost;
}
