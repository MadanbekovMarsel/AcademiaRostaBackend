package kg.school.restschool.services;

import kg.school.restschool.dto.MarkDTO;
import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
import kg.school.restschool.repositories.MarkRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MarkService {
    private static final Logger LOG = LoggerFactory.getLogger(MarkService.class);

    private final MarkRepository markRepository;
    private final UserRepository userRepository;

    @Autowired
    public MarkService(MarkRepository markRepository, UserRepository userRepository) {
        this.markRepository = markRepository;
        this.userRepository = userRepository;
    }

    public Mark createMark(MarkDTO markDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Mark mark = new Mark();

        mark.setUser(user);
        mark.setCorrectAns(markDTO.getCorrectAns());
        mark.setTotalQues(markDTO.getTotalQuest());
        mark.setTopic(markDTO.getTopic());
        mark.setCreatedDate(markDTO.getDate());

        LOG.info("Saving mark for User {}" + user.getUsername());
        return markRepository.save(mark);
    }

    public List<Mark> getMarksByUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return markRepository.getAllByUser(user);
    }
    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username "+
                username + " not found!"));
    }
}
