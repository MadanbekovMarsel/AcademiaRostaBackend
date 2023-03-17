package kg.school.restschool.entity.enums;

import kg.school.restschool.entity.Group;
import kg.school.restschool.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class)
                .addAnnotatedClass(Group.class);

        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){
            Session session = sessionFactory.getCurrentSession();

        }
    }
}
