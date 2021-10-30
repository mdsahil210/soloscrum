package com.heroku.soloscrum.services;

import com.heroku.soloscrum.model.User;
import com.heroku.soloscrum.exceptions.UsernameAlreadyExistsException;
import com.heroku.soloscrum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User user) {
        try{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmPassword("");
            return userRepository.save(user);
        } catch(Exception ex){
            throw new UsernameAlreadyExistsException("Username: '"+user.getUsername()+"' already exists");
        }

    }

}
