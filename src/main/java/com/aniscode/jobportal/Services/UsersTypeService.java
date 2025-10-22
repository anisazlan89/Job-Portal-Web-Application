package com.aniscode.jobportal.Services;

import com.aniscode.jobportal.Entity.UsersType;
import com.aniscode.jobportal.Repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    //to get all usertype from database
    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
