package kg.school.restschool.services;

import kg.school.restschool.dto.MarkDTO;
import kg.school.restschool.entity.Mark;
import kg.school.restschool.entity.User;
import kg.school.restschool.entity.enums.Topic;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.MarkRepository;
import kg.school.restschool.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
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

    public Mark createMark(MarkDTO markDTO, String username) {
        User user;
        try {
            user = getUserByUsername(username);
        } catch (RuntimeException e) {
            throw new SearchException(SearchException.USER_NOT_FOUND);
        }
        Mark mark = getMarkByUserAndDate(user, markDTO.getDate(), markDTO.getTopic());

        if (mark.getUser() != null) {
            System.out.println("mark dates are equal " + (mark.getCreatedDate() == markDTO.getDate()));
            System.out.println(mark.getCreatedDate() + " mDTO : " + markDTO.getDate());
            System.out.println(mark.getUser().getFirstName());
        }
        mark.setTopic(markDTO.getTopic());
        mark.setUser(user);
        mark.setCorrectAnswers(mark.getCorrectAnswers() + markDTO.getCorrectAnswers());
        mark.setTotalQuestions(mark.getTotalQuestions() + markDTO.getTotalQuestions());
        mark.setCreatedDate(markDTO.getDate());

        LOG.info("Saving mark for User {}" + user.getUsername());
        return markRepository.save(mark);
    }

    public List<Mark> getMarksByUser(String username) {
        User user = getUserByUsername(username);
        return markRepository.getAllByUser(user);
    }

    private User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    private Mark getMarkByUserAndDate(User user, Date date, Topic topic) {

        return markRepository.getMarkByUserAndDate(user, date, topic).orElse(new Mark());
    }
}
