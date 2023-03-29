package kg.school.restschool.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenSuccessResponse {
    private boolean success;
    private String token;
    private String role;
}
