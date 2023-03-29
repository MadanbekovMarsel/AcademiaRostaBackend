package kg.school.restschool.entity;

import jakarta.persistence.*;
import kg.school.restschool.entity.enums.ERole;
import kg.school.restschool.entity.enums.Gender;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class Additional {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    @Cascade(CascadeType.DELETE)
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "enrollment_date", nullable = false)
    private Date enrollmentDate;

    @Column(name = "deduction_date")
    private Date deductionDate;

    @Column(name = "address")
    private String address;

    @Column(name = "gender")
    private Gender gender;

    @PrePersist
    public void onCreate(){
        this.enrollmentDate = Date.valueOf(LocalDate.now());
    }
}
