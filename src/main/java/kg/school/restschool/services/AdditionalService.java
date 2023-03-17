package kg.school.restschool.services;

import kg.school.restschool.dto.AdditionalDTO;
import kg.school.restschool.entity.Additional;
import kg.school.restschool.entity.User;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.AdditionalRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;

@Service
public class AdditionalService {

    private static final Logger LOG = LoggerFactory.getLogger(AdditionalService.class);

    private final UserRepository userRepository;
    private final AdditionalRepository additionalRepository;


    @Autowired
    public AdditionalService(UserRepository userRepository, AdditionalRepository additionalRepository) {
        this.userRepository = userRepository;
        this.additionalRepository = additionalRepository;
    }


    public Additional createAdditional(AdditionalDTO additionalDTO, String username){
        User user = getUserByUsername(username);
        Additional additional = new Additional();
        additional.setUser(user);
        additional.setPhoneNumber(additionalDTO.getPhoneNumber());
        additional.setEnrollmentDate(additionalDTO.getEnrollmentDate());
        additional.setDeductionDate(additionalDTO.getDeductionDate());
        additional.setAddress(additionalDTO.getAddress());
        additional.setGender(additionalDTO.getGender());
        try {
            return additionalRepository.save(additional);
        } catch (Exception e) {
            LOG.error("ERROR during registration. {}", e.getMessage());
            throw new RuntimeException("Additional creating error");
        }
    }
    public Additional updateAdditional(AdditionalDTO additionalDTO, String username) {
        User user = getUserByUsername(username);
        Additional additional = getAdditionalByUsername(user.getUsername());
        if (additionalDTO.getAddress() != null) additional.setAddress(additionalDTO.getAddress());
        if(additionalDTO.getDeductionDate() != null)    additional.setDeductionDate((Date) additionalDTO.getDeductionDate());
        if(additionalDTO.getPhoneNumber() != null)  additional.setPhoneNumber(additionalDTO.getPhoneNumber());
        return additionalRepository.save(additional);
    }
    public Additional getAdditionalByPrincipal(Principal principal){
        User user = getUserByPrincipal(principal);
        return additionalRepository.getAdditionalByUser(user).orElseThrow(() -> new SearchException(SearchException.ADDITIONAL_NOT_FOUND));
    }

    public Additional getAdditionalByUsername(String username) {
        User user = getUserByUsername(username);
        return additionalRepository.getAdditionalByUser(user).orElseThrow(()-> new SearchException(SearchException.ADDITIONAL_NOT_FOUND));
    }

    private User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }
    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }
}
