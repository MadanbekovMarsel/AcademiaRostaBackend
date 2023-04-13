package kg.school.restschool.web;

import kg.school.restschool.dto.MarkDTO;
import kg.school.restschool.entity.Mark;
import kg.school.restschool.facade.MarkFacade;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.MarkService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/mark")
public class MarkController {

    private final MarkService markService;
    private final ResponseErrorValidation responseErrorValidation;

    private final MarkFacade markFacade;

    @Autowired
    public MarkController(MarkService markService, ResponseErrorValidation responseErrorValidation, MarkFacade markFacade) {
        this.markService = markService;
        this.responseErrorValidation = responseErrorValidation;
        this.markFacade = markFacade;
    }

    @PatchMapping("/{username}/setMark")
    public ResponseEntity<Object> setMarkToUser(@RequestBody MarkDTO markDTO,
                                                BindingResult bindingResult,
                                                @PathVariable("username")String username){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        try{
            System.out.println("hello there is a new mark for" + username);
            markService.createMark(markDTO,username);
            return new ResponseEntity<>(new MessageResponse("Done!"), HttpStatus.OK);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getMarksOfUser(@PathVariable("username")String username){
        try {
            List<Mark> list = markService.getMarksByUser(username);

            List<MarkDTO> responseList = new ArrayList<>();
            for (Mark current : list) {
                responseList.add(markFacade.markToMarkDTO(current));
            }
            return new ResponseEntity<>(responseList, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
