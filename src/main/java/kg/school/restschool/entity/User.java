package kg.school.restschool.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kg.school.restschool.entity.enums.ERole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;

@Data
@Entity
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "fathers_name",nullable = false)
    private String fathersName;

    @Column(name = "age",nullable = false)
    private int age;

    @Column(unique = true,updatable = true,nullable = false)
    private String username;

    @Column(updatable = true,nullable = false,length = 3000)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
                    joinColumns = @JoinColumn(name = "id_user"))
    private Set<ERole> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Group",
                joinColumns = @JoinColumn(name = "id_user"),
                inverseJoinColumns = @JoinColumn(name = "id_group"))
    private List<Group> groupsList = new ArrayList<>();
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDate createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDate.now();
    }

    public User() {}

    public User(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public void addGroup(Group group){
        groupsList.add(group);
    }
    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}