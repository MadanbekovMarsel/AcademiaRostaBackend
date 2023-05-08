package kg.school.restschool.entity;

import jakarta.persistence.*;
import kg.school.restschool.entity.enums.Topic;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class Mark {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correct_ans")
    private int correctAnswers;

    @Column(name = "total_ques")
    private int totalQuestions;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "topic")
//    @Enumerated(EnumType.STRING)
    private String topic;
    @Column(name = "date",nullable = false)
    private Date createdDate;
}
