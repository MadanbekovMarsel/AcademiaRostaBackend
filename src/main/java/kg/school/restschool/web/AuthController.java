package kg.school.restschool.web;

import jakarta.validation.Valid;
import kg.school.restschool.entity.User;
import kg.school.restschool.payload.EmailServiceImpl;
import kg.school.restschool.payload.request.LoginRequest;
import kg.school.restschool.payload.request.SignUpRequest;
import kg.school.restschool.payload.response.JwtTokenSuccessResponse;
import kg.school.restschool.payload.response.MessageResponse;
import kg.school.restschool.security.JwtTokenProvider;
import kg.school.restschool.security.SecurityConstants;
import kg.school.restschool.services.UserService;
import kg.school.restschool.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
//@PreAuthorize("permitAll()")
public class AuthController {

//    private final EmailServiceImpl messageSender;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final ResponseErrorValidation responseErrorValidation;

    private final UserService userService;

    @Autowired
    public AuthController(EmailServiceImpl messageSender, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, ResponseErrorValidation responseErrorValidation, UserService userService) {
//        this.messageSender = messageSender;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.responseErrorValidation = responseErrorValidation;
        this.userService = userService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

//        messageSender.sendSimpleMessage("1904.01013@manas.edu.kg","hello","hello world");
        User user = userService.getUserByUsername(loginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtTokenSuccessResponse(true, jwt,user.getRole().toString()));
    }

    @PostMapping("/signUp")
    public ResponseEntity<Object> registerUser(@RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        System.out.println("Registering");
        try {
            userService.createUser(signUpRequest);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        }catch (RuntimeException e){
            return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
}
