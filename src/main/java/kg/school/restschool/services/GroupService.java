package kg.school.restschool.services;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.dto.UserDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Subject;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.entity.User;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.SubjectRepository;
import kg.school.restschool.repositories.TimeTableRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    private static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    private final TimeTableRepository timeTableRepository;

    @Autowired
    public GroupService(UserRepository userRepository, SubjectRepository subjectRepository, GroupRepository groupRepository, TimeTableRepository timeTableRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.groupRepository = groupRepository;
        this.timeTableRepository = timeTableRepository;
    }

    public Group defaultGroupCreator(GroupDTO groupToCreate){
        Group createdGroup = new Group();
        Subject subject = null;
        if(groupToCreate.getSubject() != null){
            subject = getSubjectByName(groupToCreate.getSubject().getName());
        }
        if(groupToCreate.getTimetable() != null){
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
    public Group createGroup(GroupDTO groupDTO){
        Group group = new Group();
        group.setName(groupDTO.getName());
        Subject subject = null;
        if(groupDTO.getSubject() != null){
            subject = getSubjectByName(groupDTO.getSubject().getName());
        }
        group.setSubject(subject);
        try {
            LOG.info("Saving group {}",group.getName());
            return groupRepository.save(group);
        }catch (Exception e){
            LOG.error("ERROR during registration. {}",e.getMessage());
            throw new ExistException(ExistException.GROUP_EXISTS);
        }
    }
    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }


    public Group addUserToGroupById(Long groupId, UserDTO userDTO) {
        User user = getUserByUsername(userDTO.getUsername());
        Group group = getGroupById(groupId);
        try {
            group.getMembers().add(user);
            groupRepository.save(group);
            return group;
        }catch (Exception e){
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
    public Group setSubjectForGroup(String groupName, String subjectName){
        Group group = getGroupByName(groupName);
        Subject subject = getSubjectByName(subjectName);
        group.setSubject(subject);
        return groupRepository.save(group);
    }

    public Group removeUserFromGroup(String groupName, String username){
        User userToRemove = getUserByUsername(username);
        Group removeFrom = getGroupByName(groupName);
        removeFrom.getMembers().remove(userToRemove);
        userToRemove.getGroupsList().remove(removeFrom);
        userRepository.save(userToRemove);
        return groupRepository.save(removeFrom);
    }

    public Group updateGroup(Long idGroup, GroupDTO groupDTO) {
        Group group = getGroupById(idGroup);
        Subject subject = null;
        if(groupDTO.getSubject() != null){
            subject = getSubjectByName(groupDTO.getSubject().getName());
        }
        group.setSubject(subject);
        return groupRepository.save(group);
    }

    public Group addUserToGroup(String username, String groupName){
        Group group = getGroupByName(groupName);
        User user = getUserByUsername(username);
        try{
            group.addUser(user);
            return groupRepository.save(group);
        }catch (Exception e){
            throw new ExistException(ExistException.USER_EXISTS);
        }
    }

    public List<Group> getGroupsByUsername(String username){
        User user = getUserByUsername(username);
        return user.getGroupsList();
    }

    //Get Methods

    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findSubjectById(subjectId).orElseThrow(()->new SearchException(SearchException.SUBJECT_NOT_FOUND));
    }
    private Subject getSubjectByName(String subjectName) {
        return subjectRepository.findSubjectByName(subjectName).orElseThrow(()->new SearchException(SearchException.SUBJECT_NOT_FOUND));
    }
    public Group getGroupByName(String name)  {
        return groupRepository.findGroupsByName(name).orElseThrow(() -> new SearchException(SearchException.GROUP_NOT_FOUND));
    }
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(()->new SearchException(SearchException.GROUP_NOT_FOUND));
    }

    private User getUserByUsername(String username){
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }
}