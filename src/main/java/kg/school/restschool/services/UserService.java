package kg.school.restschool.services;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.facade.GroupFacade;
import kg.school.restschool.facade.UserFacade;
import kg.school.restschool.payload.request.SignUpRequest;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final GroupRepository groupRepository;
    private final PasswordEncoder passwordEncoder;

    private final GroupFacade groupFacade;

    private final UserFacade userFacade;

    @Autowired
    public UserService(UserRepository userRepository, GroupRepository groupRepository, PasswordEncoder passwordEncoder, GroupFacade groupFacade, UserFacade userFacade) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.passwordEncoder = passwordEncoder;
        this.groupFacade = groupFacade;
        this.userFacade = userFacade;
    }

    public User createUser(SignUpRequest userIn){
        User user = new User();
        user.setFirstName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(passwordEncoder.encode(userIn.getPassword()));
        user.setAge(userIn.getAge());
        user.setEmail(userIn.getEmail());
        user.setFathersName(userIn.getFathersName());

        if ((userIn.getRole() != null)) {
            user.setRole(userIn.getRole());
        } else {
            user.setRole(ERole.ROLE_PUPIL);
        }

        try {
            LOG.info("Saving User {}",userIn.getUsername());
            return userRepository.save(user);
        }catch (Exception e){
            LOG.error("ERROR during registration. {}",e.getMessage());
            throw new ExistException(ExistException.USER_EXISTS);
        }
    }

    public List<User> getMembersOfGroup(Long groupId){
        Group group = getGroupByGroupId(groupId);
        return group.getMembers();
    }

    public List<UserDTO> getAllPupils(){
        List<User> pupils = userRepository.getUsersByRole(ERole.ROLE_PUPIL);

        List<UserDTO> res = new ArrayList<>();
        for(User current : pupils){
            res.add(userFacade.userToUserDTO(current));
        }
        return res;
    }

    public List<UserDTO> getAllTeachers(){
        List<User> teachers = userRepository.getUsersByRole(ERole.ROLE_TEACHER);

        List<UserDTO> res = new ArrayList<>();
        for(User current : teachers){
            res.add(userFacade.userToUserDTO(current));
        }
        return res;
    }

    public User addGroupToUser(String username, String groupname){

        User user = getUserByUsername(username);
        Group group = getGroupByGroupname(groupname);
        try {
            user.addGroup(group);
            group.addUser(user);
            groupRepository.save(group);
            return userRepository.save(user);
        }catch (Exception e){
            throw new ExistException(ExistException.GROUP_CONTAINS_USER);
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
        User userToUpdate = getUserById(id);
        userToUpdate.setFirstName((userDTO.getFirstname() != null) ? userDTO.getFirstname() : userToUpdate.getFirstName());
        userToUpdate.setLastName((userDTO.getLastname() != null) ? userDTO.getLastname() : userToUpdate.getLastName());
        userToUpdate.setAge((userDTO.getAge() != 0) ? userDTO.getAge() : userToUpdate.getAge());
        return userRepository.save(userToUpdate);
    }



    private Group getGroupByGroupId(Long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }
    private Group getGroupByGroupname(String groupname) throws SearchException {
        return groupRepository.findGroupsByName(groupname).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }

    public User getUserById(Long id){
        return userRepository.getUserById(id).orElseThrow(()->new SearchException(SearchException.USER_NOT_FOUND));
    }

    public User getCurrentUser(Principal principal){
        return getUserByPrincipal(principal);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(()->new SearchException(SearchException.USER_NOT_FOUND));
    }
    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    public List<GroupDTO> getGroupsByUsername(String username) {
        List<Group> groups = getUserByUsername(username).getGroupsList();

        List<GroupDTO> response = new ArrayList<>();
        for(Group current : groups){
            response.add(groupFacade.groupToGroupDTO(current));
        }
        return response;
    }
}