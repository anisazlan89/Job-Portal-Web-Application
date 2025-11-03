package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.JobPostActivity;
import com.aniscode.jobportal.Repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        // Logic to add a new JobPostActivity
        return jobPostActivityRepository.save(jobPostActivity);
    }
}
