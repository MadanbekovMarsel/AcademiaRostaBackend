package kg.school.restschool.dto;

import jakarta.validation.constraints.NotEmpty;
import kg.school.restschool.entity.enums.ERole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
    private String username;
    private int age;
    private String role;
}
