package kg.school.restschool.services;

import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.payload.request.SignUpRequest;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, GroupRepository groupRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setFirstName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setAge(userIn.getAge());
        user.setFathersName(userIn.getFathersname());
        user.getRoles().add(ERole.ROLE_PUPIL);
        try {
            LOG.info("Saving User {}",userIn.getUsername());
            return userRepository.save(user);
        }catch (Exception e){
            LOG.error("ERROR during registration. {}",e.getMessage());
            throw new ExistException(ExistException.USER_EXISTS);
        }
    }

    public User addGroupToUser(String username, String groupname){

        User user = getUserByUsername(username);
        Group group = getGroupByGroupname(groupname);
        try {
            user.addGroup(group);
            group.addUser(user);
            return userRepository.save(user);
        }catch (Exception e){
            throw new ExistException(ExistException.GROUP_EXISTS);
        }
    }


    public User userUpdate(UserDTO userDTO, Principal principal) throws SearchException {
        User user = getUserByPrincipal(principal);
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setUsername((userDTO.getUsername() != null) ? userDTO.getUsername() : user.getUsername());

        user.setAge((userDTO.getAge() != 0) ? userDTO.getAge() : user.getAge());
        return userRepository.save(user);
    }

    public User updateUserById(Long id, UserDTO userDTO){
        User userToUpdate = userRepository.getUserById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        userToUpdate.setFirstName((userDTO.getFirstname() != null) ? userDTO.getFirstname() : userToUpdate.getFirstName());
        userToUpdate.setLastName((userDTO.getLastname() != null) ? userDTO.getLastname() : userToUpdate.getLastName());
        userToUpdate.setAge((userDTO.getAge() != 0) ? userDTO.getAge() : userToUpdate.getAge());
        return userRepository.save(userToUpdate);
    }

    public List<Group> getGroupsByUsername(String username){
        User user = getUserByUsername(username);
        return user.getGroupsList();
    }

    private Group getGroupByGroupname(String groupname) throws SearchException {
        return groupRepository.findGroupsByName(groupname).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }

    public User getUserById(Long id) throws SearchException {
        return userRepository.getUserById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}