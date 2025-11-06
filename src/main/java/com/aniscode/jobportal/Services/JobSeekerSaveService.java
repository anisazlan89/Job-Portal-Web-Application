package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.JobPostActivity;
import com.aniscode.jobportal.Entity.JobSeekerProfile;
import com.aniscode.jobportal.Entity.JobSeekerSave;
import com.aniscode.jobportal.Repository.JobSeekerSaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job) {
        return jobSeekerSaveRepository.findByJob(job);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }

    /*public boolean existsByUserAndJob(JobSeekerProfile user, JobPostActivity job) {
        return jobSeekerSaveRepository.existsByUserIdAndJob(user, job);
    }*/
}
