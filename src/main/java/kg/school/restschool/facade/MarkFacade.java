package kg.school.restschool.facade;

import kg.school.restschool.dto.MarkDTO;
import kg.school.restschool.entity.Mark;
import org.springframework.stereotype.Component;

@Component
public class MarkFacade {
    public MarkDTO markToMarkDTO(Mark mark){
        MarkDTO markDTO = new MarkDTO();
        markDTO.setTopic(mark.getTopic());
        markDTO.setDate(mark.getCreatedDate());
        markDTO.setCorrectAnswers(mark.getCorrectAnswers());
        markDTO.setTotalQuestions(mark.getTotalQuestions());
        return markDTO;
    }
}
