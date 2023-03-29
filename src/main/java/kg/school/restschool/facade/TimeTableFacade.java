package kg.school.restschool.facade;

import kg.school.restschool.dto.TimetableDTO;
import kg.school.restschool.entity.Timetable;
import org.springframework.stereotype.Component;

@Component
public class TimeTableFacade {
    public TimetableDTO getTimeTableFacade(Timetable timeTable){
        TimetableDTO timetableDTO = new TimetableDTO();
        timetableDTO.setMonday(timeTable.getMonday());
        timetableDTO.setTuesday(timeTable.getTuesday());
        timetableDTO.setWednesday(timeTable.getWednesday());
        timetableDTO.setThursday(timeTable.getThursday());
        timetableDTO.setFriday(timeTable.getFriday());
        timetableDTO.setSaturday(timeTable.getSaturday());
        timetableDTO.setSunday(timeTable.getSunday());

        return timetableDTO;
    }
}
