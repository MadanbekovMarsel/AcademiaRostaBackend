package kg.school.restschool.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kg.school.restschool.annotations.PasswordMatches;
import lombok.Data;

@Data
@PasswordMatches
public class SignUpRequest {

    @NotEmpty(message = "Please enter your name!")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname!")
    private String lastname;

    @NotEmpty(message = "Please enter your fathersname")
    private String fathersName;

    @NotEmpty(message = "Username can't be empty!")
    private String username;

    @NotEmpty(message = "Password id required!")
    @Size(min = 6)
    private String password;

    @NotEmpty(message = "confirm your password!")
    private String confirmPassword;

    @Min(value = 0,message = "Age couldn't be less than 0")
    @Max(value = 100,message = "Age couldn't be more than 100")
    private int age;
}
