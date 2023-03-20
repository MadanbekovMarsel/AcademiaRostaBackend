package kg.school.restschool;

import kg.school.restschool.settings.Text;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestSchoolApplication {
    public static void main(String[] args) {
        Text.init();
        SpringApplication.run(RestSchoolApplication.class, args);
    }

}
