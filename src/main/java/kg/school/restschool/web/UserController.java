package kg.school.restschool.web;

import jakarta.validation.Valid;
import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.exceptions.InvalidDataException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.facade.GroupFacade;
import kg.school.restschool.facade.UserFacade;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.GroupService;
import kg.school.restschool.services.UserService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;
    private final ResponseErrorValidation responseErrorValidation;


    @Autowired
    public UserController(UserService userService, UserFacade userFacade, GroupFacade groupFacade, ResponseErrorValidation responseErrorValidation, GroupService groupService) {
        this.userService = userService;
        this.userFacade = userFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/role")
    public ResponseEntity<Object> getCurrentUsersRole(Principal principal){
        User currentUser = userService.getCurrentUser(principal);
        return new ResponseEntity<>(currentUser.getRole(),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<Object> getCurrentUser(Principal principal) {
        try {
            User user = userService.getCurrentUser(principal);
            UserDTO userDTO = userFacade.userToUserDTO(user);
            System.out.println("Im found " + userDTO.getFirstname());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println("Searching failed!");
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.OK);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_TEACHER')")
    @GetMapping("/pupils")
    public ResponseEntity<Object> getAllPupils() {
        List<UserDTO> pupils = userService.getAllPupils();
        try {
            return new ResponseEntity<>(pupils, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/teachers")
    public ResponseEntity<Object> getAllTeachers() {
        List<UserDTO> teachers = userService.getAllTeachers();
        try {
            return new ResponseEntity<>(teachers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{idUser}/groups")
    public ResponseEntity<Object> getGroupByUserId(@PathVariable("idUser") String idUser) {
        try {
            User user = userService.getUserById(Long.parseLong(idUser));
            List<Group> groups = user.getGroupsList();
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<Object> getMembersOfGroup(@PathVariable("groupId") String groupId, Principal principal) {
        try {
            System.out.println("Im looking for members");
            List<User> members = userService.getMembersOfGroup(Long.parseLong(groupId));
            List<UserDTO> responseMembers = new ArrayList<>();
            for (User u : members) {
                if(u.getRole() == ERole.ROLE_PUPIL) responseMembers.add(userFacade.userToUserDTO(u));
            }
            return new ResponseEntity<>(responseMembers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/groups")
    public ResponseEntity<Object> getGroupsOfUser(Principal principal) {
        try {

            List<GroupDTO> groups = userService.getGroupsByUsername(principal.getName());
            return new ResponseEntity<>(groups,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        UserDTO userDTO = userFacade.userToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/{username}/update")
    public ResponseEntity<Object> updateUser(@PathVariable("username")String targetUsername,
                                             @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            User user = userService.userUpdate(userDTO, principal,targetUsername);
            UserDTO userUpdated = userFacade.userToUserDTO(user);
            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        } catch (RuntimeException | InvalidDataException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username){
        try{
            userService.deleteUserByUsername(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}