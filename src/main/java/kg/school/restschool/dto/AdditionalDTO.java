package kg.school.restschool.dto;

import kg.school.restschool.entity.enums.Gender;
import lombok.Data;

import java.sql.Date;

@Data
public class AdditionalDTO {
    private int id;
    private String phoneNumber;
    private Date enrollmentDate;
    private Date deductionDate;
    private String address;
    private Gender gender;
}
