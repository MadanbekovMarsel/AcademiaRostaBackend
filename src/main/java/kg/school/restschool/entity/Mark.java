package kg.school.restschool.entity;

import jakarta.persistence.*;
import kg.school.restschool.entity.enums.Topic;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Mark {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correct_ans")
    private int correctAns;

    @Column(name = "total_ques")
    private int totalQues;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "topic")
    @Enumerated(EnumType.STRING)
    private Topic topic;
    @Column(name = "date",nullable = false)
    private LocalDate createdDate;

    @PrePersist
    public void onCreate(){
        this.createdDate = LocalDate.now();
    }
}
