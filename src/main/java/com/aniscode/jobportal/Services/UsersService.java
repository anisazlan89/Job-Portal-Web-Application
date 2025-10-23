package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.JobSeekerProfile;
import com.aniscode.jobportal.Entity.RecruiterProfile;
import com.aniscode.jobportal.Entity.Users;
import com.aniscode.jobportal.Repository.JobSeekerProfileRepository;
import com.aniscode.jobportal.Repository.RecruiterProfileRepository;
import com.aniscode.jobportal.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public UsersService(UsersRepository userRepository, JobSeekerProfileRepository jobSeekerProfileRepository, RecruiterProfileRepository recruiterProfileRepository) {
        this.userRepository = userRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public void addUser(Users users){
        //save the user entity first before creating profile
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        Users savedUser = userRepository.save(users);
        //get User Type Id
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId ==1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
    }

    public Optional<Users> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
