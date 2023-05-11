package kg.school.restschool.dto;

import jakarta.validation.constraints.NotEmpty;
import kg.school.restschool.entity.enums.ERole;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;

    private String email;
    private int age;
    private String phoneNumber;
    private String address;
    private String role;
}
