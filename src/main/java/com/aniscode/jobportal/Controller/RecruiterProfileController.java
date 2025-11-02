package com.aniscode.jobportal.Controller;

import com.aniscode.jobportal.Entity.RecruiterProfile;
import com.aniscode.jobportal.Entity.Users;
import com.aniscode.jobportal.Repository.UsersRepository;
import com.aniscode.jobportal.Services.RecruiterProfileService;
import com.aniscode.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;

    private final RecruiterProfileService recruiterProfileService;

    @Autowired
    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
        //Who is the current user, and what can they do?
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getOne(users.getUserId());

            if (!recruiterProfile.isEmpty()) {
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_profile";

    }
    @PostMapping("/addNew")
    public String addNew(RecruiterProfile recruiterProfile, @RequestParam("image") MultipartFile multipartFile, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() -> new RuntimeException("User not found"));
            //Associate recuriter profile with existing user
            recruiterProfile.setUserId(users);
            recruiterProfile.setUserAccountId(users.getUserId());

        }
        model.addAttribute("profile",recruiterProfile);

        //Set Image name in recruiter profile
        String fileName = "";
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(fileName);
        }
        RecruiterProfile savedUser = recruiterProfileService.addNew(recruiterProfile);

        String uploadDirectory = "photos/recruiter/" + savedUser.getUserAccountId();
        //Read profile image from request - multipart file
        //Save image on the server in directory photos/recruiter/
        try {
            FileUploadUtil.saveFile(uploadDirectory, fileName, multipartFile);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return "redirect:/dashboard/";
    }


}
