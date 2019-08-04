package ru.example.max.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.max.dao.UserDao;
import ru.example.max.model.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    @Qualifier("hibernateUserDao")
    private UserDao userDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userDao.getUserFromEmail(user.getEmail()) != null) {
            errors.rejectValue(
                    "email", "", "This email is already in use");
        }
    }
}
