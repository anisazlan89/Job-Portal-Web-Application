package com.aniscode.jobportal.Repository;

import com.aniscode.jobportal.Entity.JobPostActivity;
import com.aniscode.jobportal.Entity.JobSeekerProfile;
import com.aniscode.jobportal.Entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository <JobSeekerSave, Integer> {

     List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);

    /*boolean existsByUserIdAndJob(JobSeekerProfile user, JobPostActivity job);*/
}
