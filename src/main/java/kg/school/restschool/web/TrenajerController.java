package kg.school.restschool.web;

import kg.school.restschool.dto.MarkDTO;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.MarkService;
import kg.school.restschool.services.TrenajerService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/trenajer")
public class TrenajerController {

    private final TrenajerService trenajerService;
    private final MarkService markService;
    private final ResponseErrorValidation responseErrorValidation;

    @Autowired
    public TrenajerController(TrenajerService trenajerService, MarkService markService, ResponseErrorValidation responseErrorValidation) {
        this.trenajerService = trenajerService;
        this.markService = markService;
        this.responseErrorValidation = responseErrorValidation;
    }

    @GetMapping("/{taskName}/{digits}/{count}")
    public ResponseEntity<Object> getArray(@PathVariable("taskName") String taskName,
                                           @PathVariable("digits") String digits,
                                           @PathVariable("count") String count) {
        try {
            int[] res = trenajerService.getArray(taskName,Integer.parseInt(digits),Integer.parseInt(count));
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
