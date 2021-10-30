package com.heroku.soloscrum.validator;

import com.heroku.soloscrum.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        User user = (User) object;
        if(user.getPassword().length()<6) {
            errors.rejectValue("password","length","Password must be at least 6 character");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword","Match","Passwords must match");
        }
    }
}
