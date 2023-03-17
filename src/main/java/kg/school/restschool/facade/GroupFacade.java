package kg.school.restschool.facade;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.SubjectDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupFacade {
    @Autowired
    private SubjectRepository subjectRepository;
    public GroupDTO groupToGroupDTO(Group group){
        GroupDTO groupDTO = new GroupDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(group.getSubject().getName());

        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setSubject(subjectDTO);

        return groupDTO;
    }
}
