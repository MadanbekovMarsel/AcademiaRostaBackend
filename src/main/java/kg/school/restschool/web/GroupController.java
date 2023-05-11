package kg.school.restschool.web;

import jakarta.validation.Valid;
import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.facade.GroupFacade;
import kg.school.restschool.facade.TimeTableFacade;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.GroupService;
import kg.school.restschool.services.SubjectService;
import kg.school.restschool.services.TimeTableService;
import kg.school.restschool.services.UserService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/group")
@CrossOrigin
public class GroupController {

    private final GroupService groupService;
    private final GroupFacade groupFacade;
    private final UserService userService;

    private final TimeTableService timeTableService;
    private final TimeTableFacade timeTableFacade;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public GroupController(GroupService groupService, GroupFacade groupFacade, UserService userService, TimeTableService timeTableService, SubjectService subjectService, TimeTableFacade timeTableFacade, ResponseErrorValidation responseErrorValidation) {
        this.groupService = groupService;
        this.groupFacade = groupFacade;
        this.userService = userService;
        this.timeTableService = timeTableService;
        this.timeTableFacade = timeTableFacade;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> createGroup(@Valid @RequestBody GroupDTO groupDTO,
                                              BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            Group created = groupService.createGroup(groupDTO);
            return new ResponseEntity<>(groupFacade.groupToGroupDTO(created), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}/groups")
    public ResponseEntity<Object> getGroupsByUser(@PathVariable("username") String username) {
        try {
            List<GroupDTO> response = groupService.getGroupsByUsername(username);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllGroups() {
        try {
            List<Group> allGroups = groupService.getAllGroups();

            List<GroupDTO> responseGroups = new ArrayList<>();
            for (Group g : allGroups) {
                GroupDTO groupDTO = groupFacade.groupToGroupDTO(g);
                groupDTO.setTimetable(timeTableFacade.getTimeTableFacade(timeTableService.getTimetableByGroupName(groupDTO.getName())));
                responseGroups.add(groupDTO);
            }
            return new ResponseEntity<>(responseGroups, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> getCurrentUsersGroups(Principal principal) {
        try {
            List<GroupDTO> groupDTOS = groupService.getGroupsByUsername(principal.getName());
            return new ResponseEntity<>(groupDTOS, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{idGroup}")
    public ResponseEntity<Object> getGroupById(@PathVariable("idGroup") String idGroup) {
        try {
            Group group = groupService.getGroupById(Long.parseLong(idGroup));
            return new ResponseEntity<>(groupFacade.groupToGroupDTO(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{groupName}/timetable")
    public ResponseEntity<Object> getTimetableForGroup(@PathVariable("groupName") String groupName) {
        try {
            Timetable timetable = timeTableService.getTimetableByGroupName(groupName);
            return new ResponseEntity<>(timeTableFacade.getTimeTableFacade(timetable), HttpStatus.OK);
        } catch (SearchException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //
    @GetMapping("/{name}/byName")
    public ResponseEntity<Object> getGroupByName(@PathVariable("name") String name) {
        try {
            Group group = groupService.getGroupByName(name);
            return new ResponseEntity<>(groupFacade.groupToGroupDTO(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{idGroup}/update")
    public ResponseEntity<Object> updateGroupById(@RequestBody GroupDTO groupDTO,
                                                  @PathVariable("idGroup") String idGroup,
                                                  BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            Group updatedGroup = groupService.updateGroup(Long.parseLong(idGroup), groupDTO);
            return new ResponseEntity<>(groupFacade.groupToGroupDTO(updatedGroup), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{groupName}/{username}/add")
    public ResponseEntity<Object> addUserToGroup(@PathVariable("username") String username,
                                                 @PathVariable("groupName") String groupName) {
        try {
            Group group = groupService.addUserToGroup(username, groupName);
            userService.addGroupToUser(username, groupName);
            GroupDTO groupDTO = groupFacade.groupToGroupDTO(group);
            return new ResponseEntity<>(groupDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("{groupName}/{username}/setTeacher")
    public ResponseEntity<Object> setTeacherToGroup(@PathVariable("username") String username,
                                                    @PathVariable("groupName") String groupName) {
        try {
            GroupDTO groupDTO = groupFacade.groupToGroupDTO(this.groupService.setTeacherToGroup(username, groupName));
            return new ResponseEntity<>(groupDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{subjectName}/setSubject")
    public ResponseEntity<Object> setSubjectToGroup(@RequestBody GroupDTO groupDTO,
                                                    BindingResult bindingResult,
                                                    @PathVariable("subjectName") String subjectName) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            Group group = groupService.setSubjectForGroup(groupDTO.getName(), subjectName);
            return new ResponseEntity<>(groupFacade.groupToGroupDTO(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{groupName}/{username}/remove")
    public ResponseEntity<Object> removeUserFromGroup(@PathVariable("username") String username,
                                                      @PathVariable("groupName") String groupName) {
        Group group = null;
        try {
            group = groupService.removeUserFromGroup(groupName, username);
        } catch (SearchException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(groupFacade.groupToGroupDTO(group), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{groupName}/delete")
    public ResponseEntity<Object> deleteGroup(@PathVariable("groupName") String groupname) {
        try {
            groupService.deleteGroup(groupname);
            return new ResponseEntity<>(new MessageResponse("OK"), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{groupName}/setTimetable")
    public ResponseEntity<Object> setTimetableToGroup(@RequestBody TimetableDTO timetableDTO,
                                                      BindingResult bindingResult,
                                                      @PathVariable("groupName") String groupName) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        try {
            Timetable timetable = timeTableService.createTimetableToGroup(timetableDTO, groupName);
            return new ResponseEntity<>(timeTableFacade.getTimeTableFacade(timetable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}

