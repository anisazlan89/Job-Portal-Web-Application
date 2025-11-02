package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.RecruiterProfile;
import com.aniscode.jobportal.Repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }
    // to get a single recruiter profile based on the id.
    public Optional<RecruiterProfile> getOne(Integer id){
        return recruiterProfileRepository.findById(id);
    }


    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }
}
