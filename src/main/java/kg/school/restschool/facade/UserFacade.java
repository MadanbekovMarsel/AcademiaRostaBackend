package kg.school.restschool.facade;

import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getFirstName());
        userDTO.setLastname(user.getLastName());
        userDTO.setRole(user.getRole().toString());
        userDTO.setUsername(user.getUsername());
        userDTO.setAge(user.getAge());
        return userDTO;
    }
}
