package kg.school.restschool.services;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.InvalidDataException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.facade.GroupFacade;
import kg.school.restschool.facade.UserFacade;
import kg.school.restschool.payload.request.SignUpRequest;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.UserRepository;
import kg.school.restschool.validations.Validator;
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

        try{
            user.setFirstName(Validator.validName(userIn.getFirstname()));
            user.setLastName(Validator.validName(userIn.getLastname()));
            user.setFathersName(Validator.validName(userIn.getFathersName()));
            user.setEmail(userIn.getEmail());
            user.setUsername(userIn.getUsername());
            user.setPassword(passwordEncoder.encode(userIn.getPassword()));
            System.out.println(userIn.getPassword() + " == " + userIn.getConfirmPassword());

            System.out.println(userIn.getPhoneNumber() + " -> valid -> " + Validator.validNumber(userIn.getPhoneNumber()));
            user.setPhoneNumber(Validator.validNumber(userIn.getPhoneNumber()));
            user.setAddress(userIn.getAddress());
            user.setAge(userIn.getAge());
            user.setGender(userIn.getGender());
            System.out.println("User to register " + user.getUsername() + " his role " + userIn.getRole());
            if ((userIn.getRole() != null)) {
                user.setRole(userIn.getRole());
            } else {
                user.setRole(ERole.ROLE_PUPIL);
            }
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
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


    public User userUpdate(UserDTO userDTO, Principal principal, String targetUsername) throws InvalidDataException {
        User requester = getUserByPrincipal(principal);
        User targetUser = getUserByUsername(targetUsername);
        if(!requester.equals(targetUser) && !requester.getRole().equals(ERole.ROLE_ADMIN)){
            throw new InvalidDataException(InvalidDataException.TARGET_USER_NOT_EQUAL_REQUESTER);
        }

        if((userDTO.getFirstname().length()> 2)) targetUser.setFathersName(userDTO.getFirstname());
        System.out.println(userDTO.getLastname() + "<- updating lastname");
        if(userDTO.getLastname().length() > 2)   targetUser.setLastName(userDTO.getLastname());
        if(userDTO.getEmail().length() > 2)  targetUser.setEmail(userDTO.getEmail());
        if(userDTO.getAddress().length() >2)    targetUser.setAddress(userDTO.getAddress());
        if(Validator.validNumber(userDTO.getPhoneNumber()) != null)    targetUser.setPhoneNumber(Validator.validNumber(userDTO.getPhoneNumber()));

        return userRepository.save(targetUser);
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

    public void deleteUserByUsername(String username) {
        User userToDelete = getUserByUsername(username);
        userRepository.delete(userToDelete);

    }
}