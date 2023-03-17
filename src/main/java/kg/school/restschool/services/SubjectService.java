package kg.school.restschool.services;

import kg.school.restschool.dto.SubjectDTO;
import kg.school.restschool.entity.Subject;
import kg.school.restschool.exceptions.ExistException;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubjectService {
    private static final Logger LOG = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject createSubject(SubjectDTO subjectDTO){
        Subject subject = new Subject();
        subject.setCost(subjectDTO.getCost());
        subject.setName(subjectDTO.getName());
        try {
            LOG.info("Saving Subject {}",subject.getName());
            return subjectRepository.save(subject);
        }catch (Exception e){
            LOG.error("ERROR during registration. {}",e.getMessage());
            throw new ExistException(ExistException.SUBJECT_EXISTS);
        }
    }
    public Subject updateSubjectById(Long subjectId, SubjectDTO subjectDTO){
        Subject subject = subjectRepository.findSubjectById(subjectId).orElseThrow(()->new SearchException(SearchException.SUBJECT_NOT_FOUND));
        if(subjectDTO.getName() != null)    subject.setName(subjectDTO.getName());
        if(subjectDTO.getCost() != 0)   subject.setCost(subjectDTO.getCost());
        return subjectRepository.save(subject);
    }
    public Subject updateSubjectByName(String subjectName, SubjectDTO subjectDTO){
        Subject subject = subjectRepository.findSubjectByName(subjectName).orElseThrow(()->new SearchException(SearchException.SUBJECT_NOT_FOUND));
        if(subjectDTO.getName() != null)    subject.setName(subjectDTO.getName());
        if(subjectDTO.getCost() != 0)   subject.setCost(subjectDTO.getCost());
        return subjectRepository.save(subject);
    }


    public Subject getSubjectByName(String name){
        return subjectRepository.findSubjectByName(name).orElseThrow(()-> new SearchException(SearchException.SUBJECT_NOT_FOUND));
    }

    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    public void deleteSubjectById(Long idSubject) {
        subjectRepository.deleteById(idSubject);
    }
}
