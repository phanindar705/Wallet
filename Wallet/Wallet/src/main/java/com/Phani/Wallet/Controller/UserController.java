package com.Phani.Wallet.Controller;


import com.Phani.Wallet.Entity.User;
import com.Phani.Wallet.Entity.Wallet;
import com.Phani.Wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Build API to CreateUser
    @PostMapping
    public ResponseEntity<?> CreateUser(@RequestBody User user){
        if(userService.CheckUser(user)){

            return new ResponseEntity<User>(userService.AddUserDetails(user), HttpStatus.CREATED);
        }
        else {

            return new ResponseEntity<String>("user already exists",HttpStatus.CONFLICT);
        }
    }


    // Build API to get AllUsers
    @GetMapping
    public ResponseEntity<?> GetAllUsers(){
        List<User> UsersList = userService.GetAllUserDetails();
        if(UsersList==null){
            return new ResponseEntity<String>("No Users Found",HttpStatus.CONFLICT);
        }
        else {
            return new ResponseEntity<List<User>>(UsersList,HttpStatus.ACCEPTED);
        }
    }

   // Build API to GetUserById
    @GetMapping("/{id}")
    public ResponseEntity<?> GetUserById(@PathVariable Long id){
         User user = userService.GetUserDetailsById(id);
        if(user!=null){
            return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
         }
        else{
            return new ResponseEntity<String>("User not Found",HttpStatus.CONFLICT);

        }

    }

   // Build API To DeleteUser
   @DeleteMapping("/{id}")
   public ResponseEntity<String> DeleteUserById(@PathVariable Long id){
        return new ResponseEntity<String>(userService.DeleteUserDetailsById(id),HttpStatus.ACCEPTED);
   }

}
