package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.Users;
import com.aniscode.jobportal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository userRepository;

    @Autowired
    public UsersService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users addUser(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        return userRepository.save(users);
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
