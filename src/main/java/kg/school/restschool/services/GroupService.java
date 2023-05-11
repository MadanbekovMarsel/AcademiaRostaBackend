package kg.school.restschool.services;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Subject;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.facade.GroupFacade;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.SubjectRepository;
import kg.school.restschool.repositories.TimeTableRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;
    private final GroupFacade groupFacade;

    private final TimeTableRepository timeTableRepository;

    @Autowired
    public GroupService(UserRepository userRepository, SubjectRepository subjectRepository, GroupRepository groupRepository, GroupFacade groupFacade, TimeTableRepository timeTableRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
        this.groupFacade = groupFacade;
        this.timeTableRepository = timeTableRepository;
    }

    public Group defaultGroupCreator(GroupDTO groupToCreate) {
        Group createdGroup = new Group();
        Subject subject = null;
        if (groupToCreate.getSubject() != null) {
            subject = getSubjectByName(groupToCreate.getSubject().getName());
        }
        if (groupToCreate.getTimetable() != null) {
            Timetable timetable = new Timetable();
            TimetableDTO timetableDTO = groupToCreate.getTimetable();
            timetable.setMonday(timetableDTO.getMonday());
            timetable.setTuesday(timetableDTO.getTuesday());
            timetable.setWednesday(timetableDTO.getWednesday());
            timetable.setThursday(timetableDTO.getThursday());
            timetable.setFriday(timetableDTO.getFriday());
            timetable.setSunday(timetableDTO.getSunday());
            timetable.setSaturday(timetableDTO.getSaturday());

            timetable.setGroup(createdGroup);
            timeTableRepository.save(timetable);
        }
        createdGroup.setName(groupToCreate.getName());
        createdGroup.setSubject(subject);

        return groupRepository.save(createdGroup);
    }

    public Group createGroup(GroupDTO groupDTO) {
        Group group = new Group();
        group.setName(groupDTO.getName());
//        System.out.println(groupDTO.getTeacher().getUsername());
        Subject subject = null;
        User teacher = null;
        if (groupDTO.getSubject() != null) subject = getSubjectByName(groupDTO.getSubject().getName());
        if (groupDTO.getTeacher() != null) {
            teacher = getUserByUsername(groupDTO.getTeacher().getUsername());
            teacher.addGroup(group);
            group.addUser(teacher);
        }
        group.setSubject(subject);
        try {
            LOG.info("Saving group {}", group.getName());
            Group g = groupRepository.save(group);
            if (teacher != null) userRepository.save(teacher);
            return g;
        } catch (Exception e) {
            LOG.error("ERROR during registration. {}", e.getMessage());
            throw new ExistException(ExistException.GROUP_EXISTS);
        }
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }


    public Group addUserToGroupById(Long groupId, UserDTO userDTO) {
        User user = getUserByUsername(userDTO.getUsername());
        Group group = getGroupById(groupId);
        try {
            group.getMembers().add(user);
            groupRepository.save(group);
            return group;
        } catch (Exception e) {
            throw new ExistException(ExistException.GROUP_CONTAINS_USER);
        }
    }

    //    public Group setTimetableToGroup(TimetableDTO timetableDTO, String groupName){
//        Group group = getGroupByName(groupName);
//        Timetable timetable = timeTableRepository.findByGroup(group).orElse(new Timetable());
//
//        timetable.setMonday(timetableDTO.getMonday());
//        timetable.setTuesday(timetableDTO.getTuesday());
//        timetable.setWednesday(timetableDTO.getWednesday());
//        timetable.setThursday(timetableDTO.getThursday());
//        timetable.setFriday(timetableDTO.getFriday());
//        timetable.setSunday(timetableDTO.getSunday());
//        timetable.setSaturday(timetableDTO.getSaturday());
//        timetable.setGroup(group);
//        timeTableRepository.save(timetable);
//        return groupRepository.save(group);
//    }
    public Group setSubjectForGroup(String groupName, String subjectName) {
        Group group = getGroupByName(groupName);
        Subject subject = getSubjectByName(subjectName);
        group.setSubject(subject);
        return groupRepository.save(group);
    }

    public Group removeUserFromGroup(String groupName, String username) {
        User userToRemove = getUserByUsername(username);
        Group removeFrom = getGroupByName(groupName);
        removeFrom.getMembers().remove(userToRemove);
        userToRemove.getGroupsList().remove(removeFrom);
        userRepository.save(userToRemove);
        return groupRepository.save(removeFrom);
    }

    public Group updateGroup(Long idGroup, GroupDTO groupDTO) {
        Group group = getGroupById(idGroup);
        try {
            if (groupDTO.getName() != null) getGroupByName(groupDTO.getName());
        } catch (SearchException e) {
            group.setName(groupDTO.getName());
        }
        if (groupDTO.getSubject() != null) {
            Subject subject = getSubjectByName(groupDTO.getSubject().getName());
            group.setSubject(subject);
        }
        if (groupDTO.getTeacher() != null) setTeacherToGroup(groupDTO.getTeacher().getUsername(), group.getName());
        return groupRepository.save(group);
    }

    public Group addUserToGroup(String username, String groupName) {
        Group group = getGroupByName(groupName);
        User user = getUserByUsername(username);
        try {
            group.addUser(user);
            return groupRepository.save(group);
        } catch (Exception e) {
            throw new ExistException(ExistException.USER_EXISTS);
        }
    }

    public Group setTeacherToGroup(String username, String groupname) {
        try {
            Group group = getGroupByName(groupname);
            User toDelete = group.getTeacher();
            if (toDelete != null) removeUserFromGroup(groupname, toDelete.getUsername());
            System.out.println("im going to add a new teacher!");
            User teacher = getUserByUsername(username);
            teacher.addGroup(group);
            group.addUser(teacher);
            System.out.println("adding new teacher");
            userRepository.save(teacher);
            return groupRepository.save(group);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<GroupDTO> getGroupsByUsername(String username) {
        User user = getUserByUsername(username);
        List<GroupDTO> res = new ArrayList<>();
        if (user.getRole() == ERole.ROLE_ADMIN) {
            for (Group g : getAllGroups()) {
                res.add(groupFacade.groupToGroupDTO(g));
            }
        } else {
            for (Group g : user.getGroupsList()) {
                res.add(groupFacade.groupToGroupDTO(g));
            }
        }
        return res;
    }

    //Get Methods


    private Subject getSubjectByName(String subjectName) {
        return subjectRepository.findSubjectByName(subjectName).orElseThrow(() -> new SearchException(SearchException.SUBJECT_NOT_FOUND));
    }

    public Group getGroupByName(String name) {
        return groupRepository.findGroupsByName(name).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }

    private User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    public void deleteGroup(String groupName) {
        try {
            Group group = getGroupByName(groupName);
            groupRepository.delete(group);
        } catch (RuntimeException e) {
            throw new SearchException(SearchException.GROUP_NOT_FOUND);
        }
    }
}