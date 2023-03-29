package kg.school.restschool.entity;

import jakarta.persistence.*;
import kg.school.restschool.entity.enums.ERole;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name",unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_subject",referencedColumnName = "id")
    private Subject subject;


    @ManyToMany(mappedBy = "groupsList")
    private List<User> members;
    private Date createdDate;

    @PrePersist
    public void onCreated(){
        this.createdDate = Date.valueOf(LocalDate.now());
    }

    public User getTeacher(){
        for(User current : members){
            if(current.getRole() == ERole.ROLE_TEACHER) return current;
        }
        return null;
    }
    public boolean addUser(User user){
       return members.add(user);
    }
}
