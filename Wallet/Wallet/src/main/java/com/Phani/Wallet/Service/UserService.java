package com.Phani.Wallet.Service;


import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   // Checking User exits or not
    public boolean CheckUser(User user){
        List<User> AllUsers = userRepository.findAll();
        for(User ExistingUser : AllUsers){
            if(ExistingUser.getUserName().equals(user.getUserName()) || ExistingUser.getPhoneNumber().equals(user.getPhoneNumber()) || ExistingUser.getEmailId().equals(user.getEmailId()))
            {
                return false;
            }
        }
        return true;
    }


    public User AddUserDetails(User user){
       return userRepository.save(user);
    }


    public List<User> GetAllUserDetails(){
        return userRepository.findAll();
    }

   public User GetUserDetailsById(Long id){

        return userRepository.getById(id);

   }

    public String DeleteUserDetailsById(Long id){
        if(userRepository.findById(id).isEmpty()){
            return "User Not Found";
        }
        else {
            userRepository.deleteById(id);
            return "User Deleted";
        }
    }

}
