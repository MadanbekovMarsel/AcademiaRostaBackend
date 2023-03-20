package kg.school.restschool.web;

import jakarta.validation.Valid;
import kg.school.restschool.dto.SubjectDTO;
import kg.school.restschool.entity.Subject;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.SubjectService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
@CrossOrigin
public class SubjectController {
    private final SubjectService subjectService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public SubjectController(SubjectService subjectService, ResponseErrorValidation responseErrorValidation) {
        this.subjectService = subjectService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSubject(@RequestBody SubjectDTO subjectDTO,
                                                BindingResult bindingResult){


        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors))    return errors;

        try {
            return new ResponseEntity<>(subjectService.createSubject(subjectDTO), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.OK);
        }
    }
    @GetMapping("/")
    public ResponseEntity<Object> allSubjects() {
        try {
            List<Subject> subjects = subjectService.getAllSubjects();
            return new ResponseEntity<>(subjects, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.OK);
        }
    }



    @PatchMapping("/{idSubject}/update")
    public ResponseEntity<Object> updateSubject(@RequestBody SubjectDTO subjectDTO,
                                                @PathVariable("idSubject") String idSubject,
                                                BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            Subject subject = subjectService.updateSubjectById(Long.parseLong(idSubject), subjectDTO);
            return new ResponseEntity<>(subject, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.OK);
        }
    }

    @DeleteMapping ("/{idSubject}")
    public ResponseEntity<Object> deleteSubject(@PathVariable("idSubject") String idSubject,
                                                BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try {
            subjectService.deleteSubjectById(Long.parseLong(idSubject));
            return new ResponseEntity<>(new MessageResponse("Subject was deleted!"), HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.OK);
        }
    }
}
