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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
        return build(user);
    }

    public User loadUserById(Long id){
        return userRepository.getUserById(id).orElseThrow(() -> new SearchException(SearchException.USER_NOT_FOUND));
    }

    public static User build(User user){
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRole().ordinal());
        return new User(user.getId(),user.getUsername(),user.getPassword(),authorities);
    }
}
