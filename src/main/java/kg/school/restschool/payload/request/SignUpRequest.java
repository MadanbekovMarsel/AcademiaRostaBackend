package kg.school.restschool.payload.request;

import jakarta.validation.constraints.*;
import kg.school.restschool.annotations.PasswordMatches;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.entity.enums.Gender;
import lombok.Data;

@Data
@PasswordMatches
public class SignUpRequest {

    @NotEmpty(message = "Please enter your name!")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname!")
    private String lastname;

    @NotEmpty(message = "Please enter your fathers name")
    private String fathersName;

    @NotEmpty(message = "Username can't be empty!")
    private String username;

    private Gender gender;

    private ERole role;

    private String phoneNumber;

    private String address;

    @NotEmpty(message = "Password id required!")
    @Size(min = 6)
    private String password;

    @NotEmpty(message = "confirm your password!")
    private String confirmPassword;

    @Email(message = "Not valid email!")
    private String email;

    @Min(value = 0,message = "Age couldn't be less than 0")
    @Max(value = 100,message = "Age couldn't be more than 100")
    private int age;
}
