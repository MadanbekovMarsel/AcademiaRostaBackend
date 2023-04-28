package kg.school.restschool.dto;

import jakarta.validation.constraints.NotEmpty;
import kg.school.restschool.entity.User;
import lombok.Data;

@Data
public class GroupDTO {
    private Long id;
    @NotEmpty
    private String name;
    private SubjectDTO subject;
    private UserDTO teacher;
    private TimetableDTO timetable;
}
