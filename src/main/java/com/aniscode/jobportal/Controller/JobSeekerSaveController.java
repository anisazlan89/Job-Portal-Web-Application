package com.aniscode.jobportal.Controller;

import com.aniscode.jobportal.Entity.JobPostActivity;
import com.aniscode.jobportal.Entity.JobSeekerProfile;
import com.aniscode.jobportal.Entity.JobSeekerSave;
import com.aniscode.jobportal.Entity.Users;
import com.aniscode.jobportal.Services.JobPostActivityService;
import com.aniscode.jobportal.Services.JobSeekerProfileService;
import com.aniscode.jobportal.Services.JobSeekerSaveService;
import com.aniscode.jobportal.Services.UsersService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class JobSeekerSaveController {

    private final UsersService usersService;
    private final JobSeekerProfileService jobSeekerProfileService;
    private final JobPostActivityService jobPostActivityService;
    private final JobSeekerSaveService jobSeekerSaveService;

    public JobSeekerSaveController(UsersService usersService, JobSeekerProfileService jobSeekerProfileService, JobPostActivityService jobPostActivityService, JobSeekerSaveService jobSeekerSaveService) {
        this.usersService = usersService;
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.jobPostActivityService = jobPostActivityService;
        this.jobSeekerSaveService = jobSeekerSaveService;
    }

    @PostMapping("job-details/save/{id}")
    public String save(@PathVariable("id") int id, JobSeekerSave jobSeekerSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //Reads the current user’s username/email.
            String currentUsername = authentication.getName();
            //Loads the corresponding Users entity from DB
            Users user = usersService.findByEmail(currentUsername);
            //Fetches the Job Seeker’s profile for this user.
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            //Fetches the JobPostActivity (the job being saved) by path variable id.
            JobPostActivity jobPostActivity = jobPostActivityService.getOne(id);
            //If both exist, it links the save record to:
            if (seekerProfile.isPresent() && jobPostActivity != null) {
                jobSeekerSave = new JobSeekerSave();
                jobSeekerSave.setJob(jobPostActivity);
                jobSeekerSave.setUserId(seekerProfile.get());
            } else {
                throw new RuntimeException("User not found");
            }
            //Persists the “saved job” record to the database ( job_seeker_save table).
            jobSeekerSaveService.addNew(jobSeekerSave);
        }
        return "redirect:/dashboard/";
    }

    /*@PostMapping("job-details/save/{id}")
    public String save(@PathVariable int id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) return "redirect:/login";

        var user = usersService.findByEmail(auth.getName());
        var seeker = jobSeekerProfileService.getOne(user.getUserId())
                .orElseThrow(() -> new IllegalStateException("Profile not found"));
        var job = jobPostActivityService.getOne(id);
        if (job == null) throw new IllegalArgumentException("Job not found");

        // prevent duplicates
        if (jobSeekerSaveService.existsByUserAndJob(seeker, job)) {
            return "redirect:/dashboard/?alreadySaved=true";
        }

        var entity = new JobSeekerSave();
        entity.setUserId(seeker);
        entity.setJob(job);
        jobSeekerSaveService.addNew(entity);
        return "redirect:/dashboard/?saved=true";
    }*/

    @GetMapping("saved-jobs/")
    public String savedJobs(Model model) {

        List<JobPostActivity> jobPost = new ArrayList<>();
        Object currentUserProfile = usersService.getCurrentUserProfile();

        List<JobSeekerSave> jobSeekerSaveList = jobSeekerSaveService.getCandidatesJob((JobSeekerProfile) currentUserProfile);
        for (JobSeekerSave jobSeekerSave : jobSeekerSaveList) {
            jobPost.add(jobSeekerSave.getJob());
        }

        model.addAttribute("jobPost", jobPost);
        model.addAttribute("user", currentUserProfile);

        return "saved-jobs";
    }
}
