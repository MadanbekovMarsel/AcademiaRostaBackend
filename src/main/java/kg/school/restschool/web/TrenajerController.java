package kg.school.restschool.web;

import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.services.TrenajerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/trenajer")
public class TrenajerController {

    private final TrenajerService trenajerService;

    @Autowired
    public TrenajerController(TrenajerService trenajerService) {
        this.trenajerService = trenajerService;
    }

    @GetMapping("/{taskName}")
    public ResponseEntity<Object> getArray(@PathVariable("taskName") String taskName) {
        try {
            int[] res = trenajerService.getArray(taskName);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

}
