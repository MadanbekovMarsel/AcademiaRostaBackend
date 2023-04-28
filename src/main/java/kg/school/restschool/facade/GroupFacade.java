package kg.school.restschool.facade;

import kg.school.restschool.dto.GroupDTO;
import kg.school.restschool.dto.SubjectDTO;
import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupFacade {
    public GroupDTO groupToGroupDTO(Group group){
        UserFacade userFacade = new UserFacade();
        GroupDTO groupDTO = new GroupDTO();
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(group.getSubject().getName());

        for(User teacher: group.getMembers()){
            if(teacher.getRole() == ERole.ROLE_TEACHER){
                groupDTO.setTeacher(userFacade.userToUserDTO(teacher));
            }
        }
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setSubject(subjectDTO);

        return groupDTO;
    }
}
