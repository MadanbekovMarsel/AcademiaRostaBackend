package kg.school.restschool.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TimetableDTO {
    private Long id;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String sunday;
    private String saturday;
}
