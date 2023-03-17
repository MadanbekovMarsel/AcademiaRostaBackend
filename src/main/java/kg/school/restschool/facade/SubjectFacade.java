package kg.school.restschool.facade;

import kg.school.restschool.dto.SubjectDTO;
import kg.school.restschool.entity.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectFacade {
    public SubjectDTO subjectToSubjectDTO(Subject subject){
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        subjectDTO.setCost(subject.getCost());
        return subjectDTO;
    }
}
