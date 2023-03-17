package kg.school.restschool.services;

import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.Timetable;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.GroupRepository;
import kg.school.restschool.repositories.TimeTableRepository;

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
    public TimeTableService(TimeTableRepository timeTableRepository, GroupRepository groupRepository) {
        this.timeTableRepository = timeTableRepository;
        this.groupRepository = groupRepository;
    }

    public Timetable createTimetableToGroup(TimetableDTO timetableDTO, String groupName) {
        Timetable timetable = new Timetable();

        Group group = getGroupByName(groupName);
        timetable.setMonday(timetableDTO.getMonday());
        timetable.setTuesday(timetableDTO.getTuesday());
        timetable.setWednesday(timetableDTO.getWednesday());
        timetable.setThursday(timetableDTO.getThursday());
        timetable.setFriday(timetableDTO.getFriday());
        timetable.setSunday(timetableDTO.getSunday());
        timetable.setSaturday(timetableDTO.getSaturday());
        timetable.setGroup(group);
        try{
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