package kg.school.restschool.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.school.restschool.annotations.PasswordMatches;
import kg.school.restschool.payload.request.SignUpRequest;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintsAnnotation){
    }
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest signUpRequest = (SignUpRequest) o;
        System.out.println("Is valid ====> " + signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()));
        return signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword());
    }
}
