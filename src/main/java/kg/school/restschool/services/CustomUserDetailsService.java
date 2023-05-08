package kg.school.restschool.services;

import kg.school.restschool.entity.User;
import kg.school.restschool.exceptions.SearchException;
import kg.school.restschool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
        System.out.println(user.getUsername() + " => role => " + user.getRole());
        return build(user);
    }

    public User loadUserById(Long id){
        return userRepository.getUserById(id).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    public static User build(User user){
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().toString()));

        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword()
        );
    }
}
