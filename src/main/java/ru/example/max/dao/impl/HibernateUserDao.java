package ru.example.max.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.example.max.dao.UserDao;
import ru.example.max.model.User;

import java.util.List;

@Component
public class HibernateUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<User> getAll() {
        return currentSession().createQuery("from User", User.class).list();
    }

    @Override
    public User getUserFromEmail(String email) {
        Query<User> query = currentSession().createQuery(
                "from User where email = :email", User.class);
        query.setParameter("email", email);
        return query.list().stream().findAny().orElse(null);
    }

    @Override
    public void add(User user) {
        currentSession().save(user);
    }
}
