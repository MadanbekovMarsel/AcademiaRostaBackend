package kg.school.restschool.services;

import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.TimeTableRepository;

import kg.school.restschool.validations.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeTableService {
    public static final Logger LOG = LoggerFactory.getLogger(TimeTableService.class);

    private final TimeTableRepository timeTableRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public TimeTableService(TimeTableRepository timeTableRepository, GroupRepository groupRepository, Validator validator) {
        this.timeTableRepository = timeTableRepository;
        this.groupRepository = groupRepository;
    }

    public Timetable createTimetableToGroup(TimetableDTO timetableDTO, String groupName) {
        Timetable timetable = new Timetable();

        boolean empty = true;
        Group group = getGroupByName(groupName);
        if(Validator.validTime(timetableDTO.getMonday()) != null){
            timetable.setMonday(Validator.validTime(timetableDTO.getMonday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getTuesday()) != null){
            timetable.setTuesday(Validator.validTime(timetableDTO.getTuesday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getWednesday()) != null){
            timetable.setWednesday(Validator.validTime(timetableDTO.getWednesday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getThursday()) != null){
            timetable.setThursday(Validator.validTime(timetableDTO.getThursday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getFriday()) != null){
            timetable.setFriday(Validator.validTime(timetableDTO.getFriday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getSaturday()) != null){
            timetable.setSaturday(Validator.validTime(timetableDTO.getSaturday()));
            empty = false;
        }
        if(Validator.validTime(timetableDTO.getSunday()) != null){
            timetable.setSunday(Validator.validTime(timetableDTO.getSunday()));
            empty = false;
        }

        System.out.println(timetableDTO.getMonday());
        System.out.println(timetableDTO.getTuesday());
        System.out.println(timetableDTO.getWednesday());
        System.out.println(timetableDTO.getThursday());
        System.out.println(timetableDTO.getFriday());
        System.out.println(timetableDTO.getSaturday());
        System.out.println(timetableDTO.getSunday());
        if(empty)   throw new RuntimeException("Empty timetable!");
        try{
            timetable.setGroup(group);
            LOG.info("Saving timetable of group " + group.getName());
            return timeTableRepository.save(timetable);
        }catch (Exception e){
            LOG.error("Error during save new timetable for group " + group.getName());
            throw new RuntimeException("Error during create timetable");
        }
    }
    public Timetable getTimetableByGroupName(String groupName) {
        Group group = getGroupByName(groupName);

        return timeTableRepository.findByGroup(group).orElseThrow(()->new SearchException(SearchException.TIMETABLE_NOT_FOUND));
    }
    public Timetable updateTimeTableToGroup(TimetableDTO timetableDTO, String groupName) throws SearchException {
        Timetable timeTable = getTimetableByGroupName(groupName);
        timeTable.setMonday(timetableDTO.getMonday());
        timeTable.setTuesday(timetableDTO.getTuesday());
        timeTable.setWednesday(timetableDTO.getWednesday());
        timeTable.setThursday(timetableDTO.getThursday());
        timeTable.setFriday(timetableDTO.getFriday());
        timeTable.setSunday(timetableDTO.getSunday());
        timeTable.setSaturday(timetableDTO.getSaturday());

        try{
            LOG.info("Updating timetable of group " + groupName);
            return timeTableRepository.save(new Timetable());
        }catch (Exception e){
            throw new RuntimeException("Error during update timetable");
        }
    }

    private Group getGroupByName(String groupName){
        return groupRepository.findGroupsByName(groupName).orElseThrow(()->new SearchException(SearchException.GROUP_NOT_FOUND));
    }
}